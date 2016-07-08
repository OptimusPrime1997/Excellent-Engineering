package model.impl;

public class DragRange extends Range {
	private SinglePoint beginPoint;
	
	public DragRange(SinglePoint start,SinglePoint end,SinglePoint begin) {
		// TODO Auto-generated constructor stub
		super(start,end);
		beginPoint = begin;
	}
	
	@Override
	public String toXML(){
		return null;
}

	public SinglePoint getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(SinglePoint beginPoint) {
		this.beginPoint = beginPoint;
	}
	
}
