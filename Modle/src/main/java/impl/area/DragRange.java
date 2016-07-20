package impl.area;

public class DragRange extends Range {
	private SinglePoint beginPoint;
	
	public DragRange(SinglePoint start,SinglePoint end,SinglePoint begin) {
		super(start,end);
		beginPoint = begin;
	}
	
	@Override
	public String toXML(){
		String XML = "<DragRange>\n" +
				      beginPoint.toXML() + super.toXML() +
				     "</DragRange>\n";
		return XML;
	}

	@Override
	public String printArea() {
		String temp = super.printArea();
		String result = "<area type=\"DragRange\">\n";
		result+=temp;
		result+=beginPoint.printArea();
		result+="</area>";
		return  result;
	}

	public SinglePoint getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(SinglePoint beginPoint) {
		this.beginPoint = beginPoint;
	}
	
}
