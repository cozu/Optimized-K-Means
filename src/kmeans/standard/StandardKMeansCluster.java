package kmeans.standard;

import java.util.LinkedList;
import java.util.List;

import kmeans.KMeansCluster;
import kmeans.model.Point;
import kmeans.model.PointsGroup;

public class StandardKMeansCluster extends KMeansCluster {
	public PointsGroup group = new PointsGroup(0,0);
	
	public StandardKMeansCluster(Point center){
		this.center = center;
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
	
	@Override
	public String toString() {
		return "Center: " + center + "  Count:" + getPointsCount(); 
	}
}
