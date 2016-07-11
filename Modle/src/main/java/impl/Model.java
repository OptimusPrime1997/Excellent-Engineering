package impl;

import java.util.ArrayList;
import java.util.List;

import impl.xmlwriter.PathStrategy;
import service.Edge;
import service.ModelService;
import impl.xmlwriter.path_strategy.*;


public class Model implements ModelService{

	protected List<State> states = new ArrayList<State>();
	protected String rootName = null;
    protected int id;
	protected PathStrategy strategy = new RootFrist();



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


	public void setOracle(String oracleName,Result result) {
		for(State state : states){
			if(state.isThisName(oracleName)){
				state = state.toOracle(result);
			}
		}
	}

	public void printXML(int times) {
		this.strategy.writeXML(states,rootName,times);
	}

	public String getRootName() {
		return rootName;
	}

}
