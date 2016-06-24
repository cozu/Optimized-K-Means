package kmeans;

import java.util.List;

import kmeans.model.Point;

public abstract class KMeansCluster {
	
	public Point center;
	
	public double getDistanceToCenter(Point p){
		double x = center.getX() - p.getX();
		double y = center.getY() - p.getY();
		
		return Math.hypot(x, y);
	}
	
	public Point getCenter() {
		return center;
	}
	
	public abstract int getPointsCount();
	
	public abstract double computeCenterAndGetDeviation();
	
	public abstract List<Point> removePointsByDeviation(double deviation);

}
