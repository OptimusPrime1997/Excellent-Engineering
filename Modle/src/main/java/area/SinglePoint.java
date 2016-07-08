package area;

import service.Area;

public class SinglePoint implements Area{
	/**
	 * 单个点的x坐标
	 */
	private int Xray;
	/**
	 * 单个点的y坐标
	 */
	private int Yray;
	
	
	public SinglePoint(int xray, int yray) {
		super();
		Xray = xray;
		Yray = yray;
	}

	public String toXML() {
		return null;
	}
	public int getXray() {
		return Xray;
	}
	public void setXray(int xray) {
		Xray = xray;
	}
	public int getYray() {
		return Yray;
	}
	public void setYray(int yray) {
		Yray = yray;
	}
	
	
}
