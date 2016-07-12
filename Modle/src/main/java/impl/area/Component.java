package impl.area;

import service.Area;
import util.ComponentType;

public class Component implements Area{
	
	private String id;
	private String type;
	
	
	public Component(String id , String type) {
		super();
		this.id = id;
		this.type = type;
	}


	public String toXML() {
		String XML = "<resourceId>" + id + "</resourceId>\n"+
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
