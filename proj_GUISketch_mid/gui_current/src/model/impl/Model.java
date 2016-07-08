package model.impl;

import java.util.ArrayList;
import java.util.List;

import model.service.ModelService;


public class Model implements ModelService{

	private List<State> states = new ArrayList<State>();
	private String rootName = null;
	
	@Override
	public void setRoot(String rootName) {
		this.rootName = rootName;		
	}

	@Override
	public void addEdge(String start, Operation operation) {
		boolean isRecord = false;
		for(State state : states){
			if(state.isThisName(start)){
				isRecord = true;
				state.addOperation(operation);
				break;
			}
		}
		
		if(!isRecord){
			State stateNew = new State(start);
			stateNew.addOperation(operation);
			states.add(stateNew);
			
		}
	}

	@Override
	public void deleteEdge(String start, String operationId) {
		for(State state : states){
			if(state.isThisName(start)){
				state.deleteOperation(operationId);
				break;
			}
		}
	}

	@Override
	public void setOracle(String oracleName , boolean oracleInfo) {
		for(State state : states){
			if(state.isThisName(oracleName)){
				state.setOracleInfo(oracleInfo);
			}
		}
	}

	public String getRootName() {
		return rootName;
	}

}
