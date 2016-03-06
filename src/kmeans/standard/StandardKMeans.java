package kmeans.standard;

import java.util.LinkedList;
import java.util.List;

import kmeans.model.Point;

public class StandardKMeans {

	private List<Point> centroids;
	
	private List<Point> points;
	
	private int maxIterations = 0;
	
	public StandardKMeans(List<Point> centroids, List<Point> points, int maxIterations){
		this.centroids = centroids;
		this.points = points;
		this.maxIterations = maxIterations;
	}
	
	public List<StandardKMeansCluster> compute(){
		List<StandardKMeansCluster> clusters = new LinkedList<>();
		int iterations = 0;
		
		for (Point center : centroids){
			clusters.add(new StandardKMeansCluster(center));
		}
		
		double minDistance = Double.MAX_VALUE;
		StandardKMeansCluster ownerCluster = null;
		
		double maxDeviation = 0.0;
		
		long startTime;
		long totalTime = 0;
		do {
			startTime = System.currentTimeMillis();
			while (!points.isEmpty()){
				Point p = points.get(0);
				minDistance = Double.MAX_VALUE;
				ownerCluster = null;
				for (StandardKMeansCluster cluster : clusters){
					double distanceToCenter = cluster.getDistanceToCenter(p);
					if (distanceToCenter < minDistance){
						ownerCluster = cluster;
						minDistance = distanceToCenter;
					}
				}
				ownerCluster.addPoint(p);
				points.remove(p);
			}
			
			
			maxDeviation = 0.0;
			for (StandardKMeansCluster cluster : clusters){
				double deviation = cluster.computeCenterAndGetDeviation();
				if (deviation > maxDeviation){
					maxDeviation = deviation;
				}
			}
			
			if (maxDeviation == 0){
				break;
			}
			
			for (StandardKMeansCluster cluster : clusters){
				points.addAll(cluster.removePointsByDeviation(maxDeviation));
			}
			
			iterations++;
			long loopTime = System.currentTimeMillis()-startTime;
			totalTime += loopTime;
		} while(maxDeviation>0 && iterations<maxIterations && !points.isEmpty());	
		System.out.println("Total time: " + totalTime);
		for (StandardKMeansCluster cluster : clusters){
			System.out.println(cluster);
		}
		return clusters;
	}
	
}
