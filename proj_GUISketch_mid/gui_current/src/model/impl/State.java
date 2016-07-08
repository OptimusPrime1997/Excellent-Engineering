package model.impl;

import java.util.ArrayList;
import java.util.List;

public class State {
	
	private String name;
	private List<Operation> operationList;
	private boolean isOracle = false;
	
	public State(String name){
		this.name = name;
		operationList = new ArrayList<Operation>();
	}
	
	public void addOperation(Operation operation){
		operationList.add(operation);
	}

	public void deleteOperation(String operationId){
		for(Operation operation : operationList){
			if(operation.hasSameID(operationId)){
				operationList.remove(operation);
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
