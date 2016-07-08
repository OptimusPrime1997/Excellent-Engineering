package model.impl;

import java.util.ArrayList;
import java.util.List;

import model.service.Edge;

public class State {
	
	private String name;
	private List<Edge> edgeList;
	private boolean isOracle = false;
	
	public State(String name){
		this.name = name;
		edgeList = new ArrayList<Edge>();
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
