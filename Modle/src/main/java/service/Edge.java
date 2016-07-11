package service;

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

	/**
	 * 该方法用于返回始节点
	 * @return 始节点的名字
     */
	public String getRescoures();

	/**
	 * 该方法用于返回终结点
	 * @return 终结点的名字
     */
	public String getDestination();


	/**
	 * 下面的一个方法用于遍历，获得一个未遍历完全的下一条边
	 * @return 这条边的下标
     */
	public int getNextEdge();
}
