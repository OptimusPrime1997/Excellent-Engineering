package impl;

import service.Area;
import service.Edge;


public class Operation implements Edge{
	private Action action;
	private String destination;
	private Area area;
	private String id;

	
	public Operation(Action action, String destination, Area area, String id) {
		super();
		this.action = action;
		this.destination = destination;
		this.area = area;
		this.id = id;
	}


	public String toXML(){
		return null;
	}


	public boolean hasSameID(String operationId){
		return this.id.equals(operationId);
	}
	
	
}
