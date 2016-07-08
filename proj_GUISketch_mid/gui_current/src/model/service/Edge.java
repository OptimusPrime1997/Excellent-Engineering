package model.service;

public interface Edge {
	/**
	 * 该方法用于将这条边转化成XML
	 * @return XML格式的字符串
	 */
	public String toXML();
	
	/**
	 * 这方法用于查找边，方便比如删除或者修改的操作
	 * @param id 边的标志
	 * @return 这个标志是否属于这条边
	 */
	public boolean hasSameID(String id);
	
}
