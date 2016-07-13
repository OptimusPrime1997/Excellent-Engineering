package impl.edge;

import service.Area;
import service.Edge;
import util.Action;


public class Operation implements Edge{
	private Action action;
	private String destination;
	private String resources;
	private Area area;
	private String id;
	private int delay;
	private int searchTimes;   //这个用于寻路的时候记录这条边所连接的终结点是否所有的路径都被访问过
	
	public Operation(Action action, String destination,String resources, Area area, String id) {
		super();
		this.action = action;
		this.destination = destination;
		this.resources = resources;
		this.area = area;
		this.id = id;
		this.searchTimes = 0;
		this.delay=-1;
	}

	public Operation(Action action, String destination,String resources, Area area, String id,int delay) {
		super();
		this.action = action;
		this.destination = destination;
		this.resources = resources;
		this.area = area;
		this.id = id;
		this.searchTimes = 0;
		this.delay=delay;
	}



	public String toXML(){
		String XML = "<operation type = \"" + area.getClass().getName() + "\" action = \""
				+ action.name() + "\" delayTime = \"" + delay + "\">\n"
				   + area.toXML()
				   + "</operation>";
		return XML;
	}


	public boolean hasSameID(String operationId){
		return this.id.equals(operationId);
	}

	public String getRescoures() {
		return resources;
	}

	public String getDestination() {
		return destination;
	}

	public int getSearchTimes() {
		return this.searchTimes;
	}

	public void addSearchTimes() {
		searchTimes ++;
	}

	public void resetSearchTimes() {
		searchTimes = 0;
	}

}
