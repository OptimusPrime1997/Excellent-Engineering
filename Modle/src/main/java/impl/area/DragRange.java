package impl.area;

public class DragRange extends Range {
	private SinglePoint beginPoint;
	
	public DragRange(SinglePoint start,SinglePoint end,SinglePoint begin) {
		super(start,end);
		beginPoint = begin;
	}
	
	@Override
	public String toXML(){
		String XML = beginPoint.toXML() + super.toXML();
		return XML;
	}

	public SinglePoint getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(SinglePoint beginPoint) {
		this.beginPoint = beginPoint;
	}
	
}
