package impl;

import java.util.ArrayList;
import java.util.List;

import service.Edge;
import service.ModelService;


public class Model implements ModelService{

	private List<State> states = new ArrayList<State>();
	private String rootName = null;
    private int id;
	

	public void setRoot(String rootName,int id) {
		this.rootName = rootName;		
	}


	public void addEdge(int id, Edge edge) {
		boolean isRecord = false;
		for(State state : states){
			if(state.isThisName(edge.getRescoures())){
				isRecord = true;
				state.addEdge(edge);
				break;
			}
		}
		boolean hasDestination = false;
		for(State state : states){
			if(state.isThisName(edge.getDestination())){
				hasDestination = true;
				break;
			}
		}

		if(!hasDestination){
			states.add(new State(edge.getDestination() , id));
		}

		if(!isRecord){
			State stateNew = new State(edge.getRescoures() , id);
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
