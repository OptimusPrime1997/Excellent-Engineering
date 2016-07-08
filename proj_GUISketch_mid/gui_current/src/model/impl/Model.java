package model.impl;

import java.util.ArrayList;
import java.util.List;

import model.service.Edge;
import model.service.ModelService;


public class Model implements ModelService{

	private List<State> states = new ArrayList<State>();
	private String rootName = null;
	
	@Override
	public void setRoot(String rootName) {
		this.rootName = rootName;		
	}

	@Override
	public void addEdge(String start, Edge edge) {
		boolean isRecord = false;
		for(State state : states){
			if(state.isThisName(start)){
				isRecord = true;
				state.addEdge(edge);
				break;
			}
		}
		
		if(!isRecord){
			State stateNew = new State(start);
			stateNew.addEdge(edge);
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
