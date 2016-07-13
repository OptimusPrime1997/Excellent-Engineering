package impl.model;

import java.util.ArrayList;
import java.util.List;


import impl.oracle_result.Result;
import impl.state.State;
import service.Edge;
import service.ModelService;
import util.xmlwriter.PathStrategy;
import util.xmlwriter.path_strategy.RootFrist;



public class Model implements ModelService{

	protected List<State> states = new ArrayList<State>();
	protected String rootName = null;
    protected int id;
	protected PathStrategy strategy = new RootFrist();



	public void setRoot(String rootName,int id) {
		this.rootName = rootName;
		this.id = id;
		System.out.println("\nroot set complete , and the name is :" + rootName
		                  + "\n" + "id is :"+ id +'\n');
	}



	public void addEdge(int startId,int endId, Edge edge) {
		System.out.println("add edge begin!!!");
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
			states.add(new State(edge.getDestination(),id));
			System.out.println("add a new destination state");
		}

		if(!isRecord){
			State stateNew = new State(edge.getRescoures(),id);
			stateNew.addEdge(edge);
			states.add(stateNew);
			System.out.println("add a new rescoures edge");
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


	public void setOracle(String oracleName, Result result) {
		for(State state : states){
			if(state.isThisName(oracleName)){
				states.remove(state);
				state = state.toOracle(result);
				states.add(state);
				break;
			}
		}
	}

	public void printXML(int times) {
		if(states.size() == 0){
			System.err.println("Model is empty!!!");
			return;
		}
		this.strategy.writeXML(states,rootName,times);
	}

	public String getRootName() {
		return rootName;
	}

}
