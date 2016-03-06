package kmeans.model;

import java.util.LinkedList;
import java.util.List;

public class PointsGroup {
	
	private static Double lastUid = new Double(9999999999.0);
	
	private double uid = PointsGroup.getUID();
	
	private double minOffset;
	private double maxOffset;
	//the missing distance for jumping to another cluster
	private double foreignClusterMinimumDistance;
	private double xSum;
	private double ySum;
	
	private List<Point> points = new LinkedList<Point>();
	
	public PointsGroup(double minOffset, double maxOffset){
		this.minOffset = minOffset;
		this.maxOffset = maxOffset;
		this.foreignClusterMinimumDistance = Double.MAX_VALUE;
	}
	
	public double getMinOffset(){
		return minOffset;
	}
	
	public double getMaxOffset(){
		return maxOffset;
	}
	
	public void setMinOffset(double minOffset){
		this.minOffset = minOffset;
	}
	
	public void setMaxOffset(double maxOffset){
		this.maxOffset = maxOffset;
	}
	
	public List<Point> getPointsList(){
		return points;
	}
	
	public void addPoint(Point p, double foreignClusterOffset){
		xSum += p.getX();
		ySum += p.getY();
		if (foreignClusterMinimumDistance>foreignClusterOffset){
			foreignClusterMinimumDistance = foreignClusterOffset;
		}
		points.add(p);
	}
	
	public Double getUid(){
		return uid;
	}
	
	public double getXSum(){
		return xSum;
	}
	
	public double getYSum(){
		return ySum;
	}
	
	public double getForeignClusterMinimumDistance(){
		return foreignClusterMinimumDistance;
	}
	
	private static Double getUID() {
		lastUid = new Double(lastUid.doubleValue() - 0.000001);
		return lastUid;
	}

	public int getPointsCount() {
		return points.size();
	}
}
