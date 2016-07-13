package impl.area;

import service.Area;

public class Component implements Area{
	
	private String id;
	private String type;
	
	
	public Component(String id , String type) {
		super();
		this.id = id;
		this.type = type;
	}


	public String toXML() {
		String XML = "<index>" + id + "</index>\n"+
				     "<componentType>"+ type + "</componentType>\n";

		return XML;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


}
