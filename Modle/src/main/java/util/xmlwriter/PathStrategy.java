package util.xmlWriter;

import impl.state.State;
import service.Edge;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2016/7/11.
 */
public abstract class PathStrategy {

    public void writeXML(List<State> appStates, String root, int times){}

    /**
     * 用于获取一条路径的XML
     * @param stackNew 路径栈
     * @return XML格式的String
     */
    protected String getXML(Stack<Edge> stackNew){
        String XML = "";
        Iterator<Edge> it = stackNew.iterator();
        while(it.hasNext()){
            XML += it.next().toXML();
        }
        return XML;
    }

    /**
     * 用于检查路径是否被记录过
     * @param XML 路径的XML
     * @param pathMemory 保存路径的List
     * @return 比较结果
     */
    protected boolean isNewPath(String XML,List<String> pathMemory){
        boolean isNewPath = true;
        for(String memory : pathMemory){
            if(memory.equals(XML)){
                isNewPath = false;
                break;
            }
        }
        return isNewPath;
    }

    /**
     * 用于通过状态名称找到状态
     * @param appStates
     * @param stateName
     * @return
     */
    protected State findStateByName(List<State> appStates,String stateName){
        State state = null;
        for(State statePtr : appStates){
            if(statePtr.isThisName(stateName)){
                state = statePtr;
                break;
            }
        }
        return state;
    }
}
