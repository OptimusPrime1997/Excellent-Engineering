package impl.area;

import service.Area;

public class Range implements Area{

	protected SinglePoint startPoint;
	protected SinglePoint endPoint;
	
	public Range(SinglePoint start,SinglePoint end){
		startPoint = start;
		endPoint = end;
	}


	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}
	public SinglePoint getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(SinglePoint startPoint) {
		this.startPoint = startPoint;
	}
	public SinglePoint getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(SinglePoint endPoint) {
		this.endPoint = endPoint;
	}
	
}
