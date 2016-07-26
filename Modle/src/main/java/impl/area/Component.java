package impl.area;

import service.Area;

public class Component implements Area{
	
	protected String id;
	protected String type;
	protected String message;
	
	
	public Component(String id , String type,String message) {
		super();
		this.id = id;
		this.type = type;
		this.message = message;
	}


	public String toXML() {
		String XML = "<Component>\n" +
				"<index>" + id + "</index>\n"+
				"<componentType>"+ type + "</componentType>\n" +
				"<message>"+message+"</message>\n"+
				"</Component>\n";

		return XML;
	}

	public String printArea() {
		String print = "<area  type = \"Component\">\n" +
				"<type>" + type + "</type>\n" +
				"<id>"+id+"</id>\n" +
				"<message>"+message+"</message>"+
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
