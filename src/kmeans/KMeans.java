package kmeans;

import java.util.LinkedList;
import java.util.List;

public class KMeans {

	private List<Point> centroids;

	private List<Point> points;

	private int maxIterations = 0;

	public KMeans(List<Point> centroids, List<Point> points, int maxIterations) {
		this.centroids = centroids;
		this.points = new LinkedList<>();
		this.points = points;
		this.maxIterations = maxIterations;
	}

	public List<KmeansCluster> compute() {
		List<KmeansCluster> clusters = new LinkedList<>();
		int iterations = 0;

		for (Point center : centroids) {
			clusters.add(new KmeansCluster(center));
		}

		double maxDeviation = 0.0;
		double minDistance = Double.MAX_VALUE;
		double smallestDistanceToAForeignCenter = 0;
		KmeansCluster ownerCluster = null;

		do {
			while (!points.isEmpty()) {
				Point p = points.get(0);
				minDistance = Double.MAX_VALUE;
				ownerCluster = null;

				double[] distanceToCenters = new double[clusters.size()];
				for (int i = 0; i < clusters.size(); i++) {
					KmeansCluster cluster = clusters.get(i);
					double distanceToCenter = cluster.getDistanceToCenter(p);
					distanceToCenters[i] = distanceToCenter;
					if (distanceToCenter < minDistance) {
						ownerCluster = cluster;
						minDistance = distanceToCenter;
					}
				}

				smallestDistanceToAForeignCenter = Double.MAX_VALUE;
				for (double distanceToCenter : distanceToCenters) {
					if (distanceToCenter < smallestDistanceToAForeignCenter	&& distanceToCenter != minDistance) {
						smallestDistanceToAForeignCenter = distanceToCenter;
					}
				}

				ownerCluster.addPoint(p, minDistance, smallestDistanceToAForeignCenter);
				points.remove(0);
			}
			
			maxDeviation = 0.0;
			for (KmeansCluster cluster : clusters) {
				double deviation = cluster.computeCenterAndGetDeviation();
				if (deviation > maxDeviation) {
					maxDeviation = deviation;
				}
			}
			
			if (maxDeviation == 0){
				break;
			}
			
			for (KmeansCluster cluster : clusters) {
				points.addAll(cluster.removePointsByDeviation(maxDeviation));
			}
			iterations++;
		} while (iterations < maxIterations	&& !points.isEmpty());

		System.out.println("Iterations: " + iterations);
		for (KmeansCluster cluster : clusters){
			System.out.println(cluster);
		}
		return clusters;
	}

}
