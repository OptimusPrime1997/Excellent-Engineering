package impl.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


//import com.sun.javafx.sg.prism.NGShape;
import impl.oracle_result.Result;
import impl.state.Oracle;
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
	protected String savePath;

	/**
	 * 构造函数生成一个模型
	 * @param states
	 * @param rootName
	 * @param id
     */
	public Model(List<State> states , String rootName,int id){
		this.rootName = rootName;
		this.id = id;
		this.states = states;
	}

	public Model(){

	}
	public Model(String savePath){
		this.savePath = savePath;
	}

	public void setSavePath(String savePath){
		this.savePath = savePath;
	}

	public void setRoot(String rootName,int id) {
		this.rootName = rootName + id;
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


	public void setOracle(String oracleName,int oracleID ,Result result) {
		for(State state : states){
			if(state.isThisName(oracleName)){
				states.remove(state);
				state = state.toOracle(result);
				states.add(state);
				System.out.println("set oracle");
				return;
			}
		}
		states.add(new Oracle(oracleName,oracleID,result));
		System.out.println("add oracle");

	}

	public void printXML(int times) {
		if(states.size() == 0){
			System.err.println("Model is empty!!!");
			return;
		}
		System.out.println("Model has "+states.size() + " states");
		System.out.println(this.savePath +"paths.xml");
		this.strategy.writeXML(states,rootName,times,this.savePath + "paths.xml");
	}

	public void printModel() {
		String print = "<model>\n";
		for(State state : states){
			print += state.printState();
		}
		print += "</model>\n";
		try{
			FileWriter fileWriter=new FileWriter(this.savePath +"model.xml");
			fileWriter.write(print);
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getRootName() {
		return rootName;
	}


}
