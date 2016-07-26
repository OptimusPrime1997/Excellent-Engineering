package Node;

import Explain.Action;
import Explain.Type;

public class ClickNode {
	/*
	 *被操作的组件（依据Type来分类）
	 */
	public String Componentid;
	/*
	 * 被操作的坐标（依据Type来分类）
	 */
	public double StartX;
	public double StartY;
	
	public ClickNode(Type typ, Action act, ActionNode nextnode,String com,double startx,double starty) {
//		super(typ, act, nextnode);
		// TODO Auto-generated constructor stub
		this.Componentid = com;
		this.StartX = startx;
		this.StartY = starty;
	}

}
