package kmeans.optimized;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kmeans.model.Point;
import kmeans.model.PointsGroup;

public class OptimizedKmeansCluster {
	public static final double INTERVALS_RESOLUTION = 0.1;

	private static final Double INTERVALS_BORDER = 1.0;
	
	public Map<Double, PointsGroup> intervals = new HashMap<Double, PointsGroup>();
	
	public Point center;
		
	public OptimizedKmeansCluster(Point center){
		this.center = center;
	}
	
	public double getDistanceToCenter(Point p){
		double x = center.getX() - p.getX();
		double y = center.getY() - p.getY();
		
		return Math.hypot(x, y);
	}
	
	public List<Point> removePointsByDeviation(double deviation){
		List<Point> pointsRemoved = new LinkedList<>();
		
		List<Double> keys = new LinkedList<Double>();
		for (Double key : intervals.keySet()){
			keys.add(new Double(key.doubleValue()));
		}
		
		for(Double groupKey : keys){
			PointsGroup group = intervals.get(groupKey);
			if (groupKey < INTERVALS_BORDER){
				//the group will be indexed by the group.getUid() key inside intervals hash,
				//and a new empty group will replace it.
				intervals.remove(groupKey);
				intervals.put(group.getUid(), group);
				intervals.put(groupKey, new PointsGroup(group.getMinOffset(), group.getMaxOffset()));
				groupKey = group.getUid();
			}
			group.setMinOffset(group.getMinOffset() - 2*deviation);
			group.setMaxOffset(group.getMaxOffset() - 2*deviation);
			
			if (group.getMinOffset() <=0){
				//select points for re-check at next iteration
				pointsRemoved.addAll(group.getPointsList());
				intervals.remove(groupKey);
			}
		}
		return pointsRemoved;
	}
	
	public void addPoint(Point p, double distanceToCenter, double smallestForeignCenterDistance){
		double foreignClusterOffset = smallestForeignCenterDistance - distanceToCenter;
		//TODO: the following will work only for interval resolution 0.1. to be updated later
		Double groupKey = new Double((double)((int)(foreignClusterOffset*10))/10.0);
		
		PointsGroup group = intervals.get(groupKey);
		if (group == null){
			double intervalStart = groupKey.doubleValue();
			group = new PointsGroup(intervalStart, intervalStart + INTERVALS_RESOLUTION);
			intervals.put(groupKey, group);
		}
		
		group.addPoint(p, foreignClusterOffset);
	}
	
	public double computeCenterAndGetDeviation(){
		double totalXSum = 0.0;
		double totalYSum = 0.0;
		int pointsCount = 0;
		
		Iterator<PointsGroup> iterator = intervals.values().iterator();
		while(iterator.hasNext()){
			PointsGroup group = iterator.next();
			totalXSum += group.getXSum();
			totalYSum += group.getYSum();
			pointsCount += group.getPointsCount();
		}
		Point newCenter = new Point(totalXSum/pointsCount, totalYSum/pointsCount);
		double deviation = getDistanceToCenter(newCenter);
		center = newCenter;
		return deviation;
	}
	
	public int getPointsCount(){
		int pointsCount = 0;
		
		Iterator<PointsGroup> iterator = intervals.values().iterator();
		while(iterator.hasNext()){
			PointsGroup group = iterator.next();
			pointsCount += group.getPointsCount();
		}
		
		return pointsCount;
	}

	public Point getCenter() {
		return center;
	}
	
	@Override
	public String toString() {
		return "Center: " + center + "  Count:" + getPointsCount(); 
	}
}
