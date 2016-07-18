package impl.edge;

import impl.area.Component;
import impl.area.MultiComponent;
import service.Area;
import service.Edge;
import util.Action;

import java.util.Comparator;
import java.util.Iterator;


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
				   + "</operation>\n";
		//多个component连续点击的情况
		if(area instanceof MultiComponent){
			XML = "";
			Iterator<Component> iterator = ((MultiComponent) area).getComponents();
			while(iterator.hasNext()){
				Component component = iterator.next();
				if(iterator.hasNext()){
				XML+="<operation type = \"" + component.getClass().getName() + "\" action = \""
						+ action.name() + "\" delayTime = \"0\">\n"
						+ component.toXML()
						+ "</operation>\n";
				}else {
					XML+="<operation type = \"" + component.getClass().getName() + "\" action = \""
							+ action.name() +  "\" delayTime = \"" + delay + "\">\n"
							+ component.toXML()
							+ "</operation>\n";
				}
			}
		}
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

	public String printEdge() {
		String print = "<edge>\n" +
				"<action>"+action.name()+"</action>\n" +
				"<destination>"+this.destination+"</destination>\n" +
				"<resource>"+this.resources+"</resource>\n" +
				area.printArea() +
				"<id>"+this.id+"</id>\n" +
				"<delay>"+this.delay+"</delay>\n" +
				"</edge>\n";
		return print;
	}

}
