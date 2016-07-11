package service;


public interface ModelService {
    /**
     * 设置起始状态的name 模型的根节点
     *
     * @param rootName 起始状态
     */
    public void setRoot(String rootName, int id);

    /**
     * 增加新的边(operation) 两点分别是 start和operation中的destination
     * @param edge
     */
    public void addEdge(int id, Edge edge);

    /**
     * 删除边 原理同addEdge
     *
     * @param start
     * @param edgeId
     */
    public void deleteEdge(String start, String edgeId);

    /**
     * 设置orcle目标状态
     *
     * @param oracleName 最终所要测试的标准状态
     *                   (类似JUNIT的expect result)
     */
    public void setOracle(String oracleName, boolean oracleInfo);


}
