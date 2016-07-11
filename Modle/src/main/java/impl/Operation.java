package impl;

import service.Area;
import service.Edge;


public class Operation implements Edge{
	private Action action;
	private String destination;
	private String resources;
	private Area area;
	private String id;
	private int ptr;   //这个指向终结点所连接的下一条边
	
	public Operation(Action action, String destination,String resources, Area area, String id) {
		super();
		this.action = action;
		this.destination = destination;
		this.resources = resources;
		this.area = area;
		this.id = id;
		this.ptr = 0;
	}


	public String toXML(){
		return null;
	}


	public boolean hasSameID(String operationId){
		return this.id.equals(operationId);
	}

	public String getRescoures() {
		return resources;
	}

	public String getDestination() {
		return destination;
	}


	public int getNextEdge() {
		ptr ++;
		return ptr - 1;
	}


}
