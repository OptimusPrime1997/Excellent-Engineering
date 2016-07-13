package impl.area;

import service.Area;

public class SinglePoint implements Area{
	/**
	 * 单个点的x坐标
	 */
	private float Xray;
	/**
	 * 单个点的y坐标
	 */
	private float Yray;
	
	
	public SinglePoint(float xray, float yray) {
		super();
		Xray = xray;
		Yray = yray;
	}


	public String toXML() {
		String XML = "<singlePoint>\n"+
					 "<pointX>"+Xray+"</pointX>\n"+
					 "<pointY>"+Yray+"</pointY>\n"+
				     "</singlePoint>\n";

		return XML;
	}


	public float getXray() {
		return Xray;
	}
	public void setXray(int xray) {
		Xray = xray;
	}
	public float getYray() {
		return Yray;
	}
	public void setYray(int yray) {
		Yray = yray;
	}
	
	
}
