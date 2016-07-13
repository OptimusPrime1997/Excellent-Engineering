package impl.edge;

import service.Edge;
/**
 * 为了以后扩展虚拟边而提供的接口，里面的内容在需要的时候自行实现
 * @author Administrator
 *
 */
public class VirtualOperation implements Edge {


	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean hasSameID(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getRescoures() {
		return null;
	}

	public String getDestination() {
		return null;
	}

	public int getSearchTimes() {
		return 0;
	}

	public void addSearchTimes() {

	}

	public void resetSearchTimes() {

	}


}
