package kmeans;

import java.util.LinkedList;
import java.util.List;

public class KmeansStandardCluster {
	public PointsGroup group = new PointsGroup(0,0);
	
	public Point center;
	
	public KmeansStandardCluster(Point center){
		this.center = center;
	}
	
	public double getDistanceToCenter(Point p){
		//return Math.sqrt(Math.pow((center.getX() - p.getX()), 2) + Math.pow(center.getY() - p.getY(), 2));
		double x = center.getX() - p.getX();
		double y = center.getY() - p.getY();
		
		return Math.hypot(x, y);
	}
	
	public List<Point> removePointsByDeviation(double deviation){
		List<Point> ret = new LinkedList<Point>();
		ret.addAll(group.getPointsList());
		group = new PointsGroup(0, 0);
		return ret;
	}
	
	public void addPoint(Point p){
		group.addPoint(p, 0);
	}
	
	public double computeCenterAndGetDeviation(){
		double totalXSum = group.getXSum();
		double totalYSum = group.getYSum();
		int pointsCount = group.getPointsCount();
		
		Point newCenter = new Point(totalXSum/pointsCount, totalYSum/pointsCount);
		double deviation = getDistanceToCenter(newCenter);
		if (deviation>0){
			deviation = 0.99;
		}
		center = newCenter;
		return deviation;
	}
	
	public int getPointsCount(){
		return group.getPointsCount();
	}

	public Point getCenter() {
		return center;
	}
	
	@Override
	public String toString() {
		return "Center: " + center + "  Count:" + getPointsCount(); 
	}
}
