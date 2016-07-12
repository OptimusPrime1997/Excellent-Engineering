package impl.area;

import service.Area;

public class Component implements Area{
	
	private String id;
//	private String belongTo;
	
	
	public Component(String id) {
		super();
		this.id = id;
		//this.belongTo = belongTo;
	}


	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


}
