package impl.area;

import service.Area;

public class Component implements Area{
	
	protected String id;
	protected String type;
	
	
	public Component(String id , String type) {
		super();
		this.id = id;
		this.type = type;
	}


	public String toXML() {
		String XML = "<Component>\n" +
				"<index>" + id + "</index>\n"+
				"<componentType>"+ type + "</componentType>\n" +
				"</Component>\n";

		return XML;
	}

	public String printArea() {
		String print = "<area  type = \"Component\">\n" +
				"<type>" + type + "</type>\n" +
				"<id>"+id+"</id>\n" +
				"</area>\n";
		return print;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


}
