package service;

public interface Area {
	/**
	 * 将该组件/点/范围打印成XML
	 * @return 返回是符合XML规范的字符串
	 */
	public String toXML();

	/**
	 * 用于打印Area
	 * @return 返回Area的打印字符串
     */
	public String printArea();
}
