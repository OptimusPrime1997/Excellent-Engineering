package Node;

import Explain.Action;
import Explain.Type;

public class ActionNode {
	/*
	 * 下一个节点
	 */
	private ActionNode Next;
	/*
	 * 用户要求操作(click,dclick,lclick,drag)
	 */
	private Action action;
	/*
	 * 组件类型
	 */
	private String type;
	/*
	 * 被操作的id(被操作的组件ID)，看type类型，若是component调用这个String
	 */
	private String Componentid;
	/*
	 * 被操作的坐标(被操作的组件坐标)，看type类型，若是singlePoint调用这个String
	 * Oracle(expect的值 )
	 */
	private String position;
//	private double StartX;
//	private double StartY;
	/*
	 * 被操作的坐标
	 */
//	private double FstX;
//	private double FstY;
//	private double SecX;
//	private double SecY;
	
	public ActionNode(ActionNode nextnode,Action act,String typ,String ID,String pos) {
		// TODO Auto-generated constructor stub
		this.type = typ;
		this.action = act;
		this.Next = nextnode;
		this.Componentid = ID;
		this.position = pos;
//		this.FstX = fstx;
//		this.FstY = fsty;
//		this.SecX = secx;
//		this.SecY = secy;
//		this.Oracle = Ora;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ActionNode getNext() {
		return Next;
	}

	public void setNext(ActionNode next) {
		Next = next;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getComponentid() {
		return Componentid;
	}

	public void setComponentid(String componentid) {
		Componentid = componentid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
	
}
