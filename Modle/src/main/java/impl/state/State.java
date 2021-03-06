package impl.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import impl.oracle_result.Result;
import service.Edge;


public class State {
	
	protected String name;
	protected List<Edge> edgeList;
    protected int id;
	
		public State(String name,int id){
		this.name = name;
		edgeList = new ArrayList<Edge>();
        this.id = id;
	}

	public State(String name,int id,List<Edge> edgeList){
		this.name = name;
		this.id = id;
		this.edgeList = edgeList;
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
		//System.out.println(this.name + "  compare to :  " + name);
		boolean result = (this.name).equals(name);
		//System.out.println("and the result is : "+result);
		return result;
	}
	
	public Iterator<Edge> iterator(){
		return edgeList.iterator();
	}
	
	public int length(){
		return edgeList.size();
	}

	public Edge get(int index){
		return edgeList.get(index);
	}

	public boolean isOracle(){
		return false;
	}

	public Oracle toOracle(Result result){

		return new Oracle(name,id,result,edgeList);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String printState(){
		String print = "<state>\n" +
				"<name>"+this.name+"</name>\n"+
				"<id>"+this.id+"</id>\n" +
				"<edges>\n";
		for(Edge edge : edgeList){
			print += edge.printEdge();
		}
		print += "</edges>\n"+
				"</state>\n";
		return print;
	}
}
