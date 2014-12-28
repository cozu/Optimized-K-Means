package kmeans;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class KMeansApp {

	public static final int CENTROIDS_COUNT = 4;
	public static final int MAX_ITERATIONS = 1000;
	
	public static void main(String[] args) {
		Random rand = new Random(923593284);
		
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream("file"));
			for (int i = 0; i < 500000; i++) {
				 out.writeDouble(rand.nextDouble());
				 out.writeDouble(rand.nextDouble());
			}
			out.close();
		} catch (IOException e) {
		}
		
		runKmeans(100000, 4);
		runStandardKmeans(100000, 4);
		runKmeans(250000, 4);
		runStandardKmeans(250000, 4);
		
		/*runKmeans(500000, 4);
		runKmeans(750000, 4);
		runKmeans(1000000, 4);
		runKmeans(2000000, 4);
		runKmeans(5000000, 4);
		
		runKmeans(100000, 8);
		runKmeans(250000, 8);
		runKmeans(500000, 8);
		runKmeans(750000, 8);
		runKmeans(1000000, 8);
		runKmeans(2000000, 8);
		runKmeans(5000000, 8);
		
		runKmeans(100000, 12);
		runKmeans(250000, 12);
		runKmeans(500000, 12);
		runKmeans(750000, 12);
		runKmeans(1000000, 12);
		runKmeans(2000000, 12);
		runKmeans(5000000, 12);
*/		
	}

	private static void runKmeans(int nbOfPoints, int nbOfCentroids) {
		List<Point> points = new LinkedList<>();
		List<Point> centroids = new LinkedList<>();
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream("file"));
			for (int i = 0; i < nbOfPoints; i++) {
				points.add(new Point(in.readDouble(), in.readDouble()));
			}
			in.close();
		} catch (IOException e) {
		}
		
		for(int i=0;i<nbOfCentroids;i++){
			centroids.add(points.get(i));
		}
		
		
		KMeans kmeans = new KMeans(centroids, points, MAX_ITERATIONS);
		long time = System.currentTimeMillis();
		kmeans.compute();
		System.out.println("Kmeans optimized (" + nbOfPoints + ", " + nbOfCentroids + ") => time:" + (System.currentTimeMillis() - time));
	}
	
	private static void runStandardKmeans(int nbOfPoints, int nbOfCentroids) {
		List<Point> points = new LinkedList<>();
		List<Point> centroids = new LinkedList<>();
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream("file"));
			for (int i = 0; i < nbOfPoints; i++) {
				points.add(new Point(in.readDouble(), in.readDouble()));
			}
			in.close();
		} catch (IOException e) {
		}
		
		for(int i=0;i<nbOfCentroids;i++){
			centroids.add(points.get(i));
		}
		
		
		StandardKMeans kmeans = new StandardKMeans(centroids, points, MAX_ITERATIONS);
		long time = System.currentTimeMillis();
		kmeans.compute();
		System.out.println("Standard Kmeans (" + nbOfPoints + ", " + nbOfCentroids + ") => time:" + (System.currentTimeMillis() - time));
	}

}
