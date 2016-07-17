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
		String XML = "<doublePoint>\n"+
					 startPoint.toXML()+
					 endPoint.toXML()+
					 "</doublePoint>\n";
		return XML;
	}

	public String printArea() {
		String print = "<area type = \"Range\">\n" +
				startPoint.printArea() + endPoint.printArea()
				+"</area>";
		return print;
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
