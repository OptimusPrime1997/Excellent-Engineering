package impl;

import java.util.ArrayList;
import java.util.List;

import service.Edge;
import service.ModelService;


public class Model implements ModelService{

	private List<State> states = new ArrayList<State>();
	private String rootName = null;
	

	public void setRoot(String rootName) {
		this.rootName = rootName;		
	}


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


	public void deleteEdge(String start, String operationId) {
		for(State state : states){
			if(state.isThisName(start)){
				state.deleteOperation(operationId);
				break;
			}
		}
	}


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
