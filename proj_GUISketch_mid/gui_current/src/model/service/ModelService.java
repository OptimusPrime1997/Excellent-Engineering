package model.service;

import model.impl.Operation;

public interface ModelService {
	/**
	 * 设置起始状态的name 模型的根节点
	 * @param rootName 起始状态
	 */
	public void setRoot(String rootName);
	/**
	 * 增加新的边(operation) 两点分别是 start和operation中的destination
	 * @param start 所要执行的状态的状态的名字，同截图的名字(or uix文件的名字 用于区别每个不同的状态)
	 * @param operantion
	 */
	public void addEdge(String start,Operation operantion);
	/**
	 * 删除边 原理同addEdge
	 * @param start
	 * @param operationId
	 */
	public void deleteEdge(String start,String operationId);
	/**
	 * 设置orcle目标状态
	 * @param oracleName最终所要测试的标准状态
	 * (类似JUNIT的expect result)
	 */
	public void setOracle(String oracleName , boolean oracleInfo);
	
	
}
