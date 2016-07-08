package model.impl;

import model.service.Area;
import model.service.Edge;
import sketch.gui.testing.TAction;

public class Operation implements Edge{
	private TAction action;
	private String destination;
	private Area area;
	private String id;

	
	public Operation(TAction action, String destination, Area area, String id) {
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
