package impl;

import service.Area;
import service.Edge;


public class Operation implements Edge{
	private Action action;
	private String destination;
	private String resources;
	private Area area;
	private String id;
	private boolean hasSearchComplete;   //这个用于寻路的时候记录这条边所连接的终结点是否所有的路径都被访问过
	
	public Operation(Action action, String destination,String resources, Area area, String id) {
		super();
		this.action = action;
		this.destination = destination;
		this.resources = resources;
		this.area = area;
		this.id = id;
		this.hasSearchComplete = false;
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

	public boolean isHasSearchComplete(){
		return hasSearchComplete;
	}

	public void searchComplete(){
		hasSearchComplete = true;
	}
}
