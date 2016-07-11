package impl;

import java.util.ArrayList;
import java.util.List;

import service.Edge;

public class State {
	
	private String name;
	private List<Edge> edgeList;
	private boolean isOracle = false;
    private int id;
	
	public State(String name,int id){
		this.name = name;
		edgeList = new ArrayList<Edge>();
        this.id = id;
	}
	
	public void addEdge(Edge edge){
		edgeList.add(edge);
	}

	public void deleteOperation(String edgeId){
		for(Edge edge : edgeList){
			if(edge.hasSameID(edgeId)){
				edgeList.remove(edgeId);
			}
		}
	}
	
	public boolean isThisName(String name){
		return this.name.equals(name);
	}
	
	public boolean isOracle(){
		return isOracle;
	}
	
	public void setOracleInfo(boolean isOracle){
		this.isOracle = isOracle;
	}
	
	
}
