package Node;

import Explain.Action;
import Explain.Type;

public class OracleNode {
	
	/*
	 * 将要被比对的对象
	 */
	public String Componentid;
	/*
	 * assert的内容
	 */
	public String content;
	
	public OracleNode(Type type,Action act, ActionNode nextnode,String con,String component) {
//		super(type, act, nextnode);
		// TODO Auto-generated constructor stub
		this.content = con;
		this.Componentid = component;
	}

}
