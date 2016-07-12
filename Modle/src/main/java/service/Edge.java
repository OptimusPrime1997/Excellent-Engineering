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
	 * 检查边所连接的终结点是否被遍历完全
	 * @return
     */
	public boolean isHasSearchComplete();

	/**
	 * 将这个边设置为遍历过
	 */
	public void searchComplete();
}
