package Node;

import Explain.Action;
import Explain.Type;

public class DragNode {
	
	/*
	 * 起始点
	 */
	public double StartX;
	public double StartY;
	/*
	 * 目标（可能是一个点，也可能是一个范围，看Type;如果是一个点，则SecX，SecY为0）
	 */
	public double FstX;
	public double FstY;
	public double SecX;
	public double SecY;
	
	public DragNode(Type typ,Action act,double fstx, double fsty, double secx, double secy,double startX,double startY,ActionNode nextnode) {
//		super(typ, act, nextnode);
		// TODO Auto-generated constructor stub
		this.StartX = startX;
		this.StartY = startY;
		this.FstX = fstx;
		this.FstY = fsty;
		this.SecX = secx;
		this.SecY = secy;
	}

}
