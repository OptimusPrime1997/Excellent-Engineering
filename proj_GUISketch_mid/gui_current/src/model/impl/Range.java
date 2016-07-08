package model.impl;

import model.service.Area;

public class Range implements Area{
	private SinglePoint startPoint;
	private SinglePoint endPoint;
	
	public Range(SinglePoint start,SinglePoint end){
		startPoint = start;
		endPoint = end;
	}
	@Override
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
