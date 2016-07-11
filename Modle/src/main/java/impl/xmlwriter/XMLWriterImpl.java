package impl.xmlwriter;

import impl.State;
import service.XMLWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Iterator;

import service.Edge;

/**
 * Created by Administrator on 2016/7/11.
 */
public class XMLWriterImpl implements XMLWriter {

    private final int SEARCH_UPPER_LIMIT = 10;

    public void writeXML(List<State> appStates,String root,int times){
        /*
            找到根节点
         */
        State rootState = this.findStateByName(appStates,root);
    }

    /**
     * 该方法用于寻找测试的路径，并生成XML格式的字符串，该方法不成熟，有待完善
     * 该方法遍历的时候采用的是循环而非递归
     * @param appStates app的状态集
     * @param rootState 根节点
     * @param times 测试次数
     * @return XML格式的关于路径的字符串
     */
    private String findTestPath(List<State> appStates,State rootState,int times){
        /*
            用于已经找到的路径的信息，防止读入重复的路径信息，里面保存的已经的XML格式的字符串了，
            保存字符串的原因是觉得保存边集比较的时候会更复杂，这是建立在相同路径下生成的XML格式
            文件是相同的前提之下的,由于每条路径要比较之前都要转换成XML，做了一些多余的操作，效率
            可能会有待提高
         */
        List<String> pathMemory = new ArrayList<String>();
        Stack<Edge> stackNew = new Stack<Edge>();   //用于保存正在遍历的边集
        State ptr = rootState;  //遍历的指针
        int up_limit = times * 2;   //设置查找的上限，防止实际拥有的路径小于要求找到的测试路径的数目而陷入死循环的情况，这个方法比较简陋，待完善
        for(int counter = 0 ; counter < times&& up_limit > 0 ;up_limit --){
            String XML = "";
            /*
                设置一个单条测试路径的查找次数上限，防止死循环，目前还没想到更好的方法。
                之所以这样做是为了减少寻一次路却没有找到Oracle的情况发生（允许没有找到Oracle的时候折返到父节点重新寻找
                ，因此searchTimes实际上是剩余的折返次数）
             */
            int searchTimes = SEARCH_UPPER_LIMIT;
            while(ptr.length() > 0 || searchTimes > 0) {
                /*
                    检查该状态是不是Oracle，如果是，就检查是否找到了一条未记录的路径，如果是，就添加之
                 */
                if (ptr.isOracle()) {
                    XML = this.getXML(stackNew);
                    if (isNewPath(XML, pathMemory)) {
                        pathMemory.add(XML);
                        counter ++ ;
                    }
                }
                /*
                    如果该状态已经是叶节点，且折返次数还有剩余，那么就折返
                 */
                if (ptr.length() == 0 && !ptr.isOracle()) {
                    Edge pop = stackNew.pop();
                    State lastState = this.findStateByName(appStates, pop.getRescoures());
                    ptr = lastState;
                    searchTimes--;
                    continue;
                }
                Edge edge = null;
                /*
                    用于找到下一个状态，使用这个循环可以允许有两个相同的状态存在于测试路径中，但是不允许
                    两条相同的测试边存在于测试路径中，从而避免死循环
                 */
                do {
                    int index = (int) (Math.random() * ptr.length());
                    edge = ptr.get(index);
                } while (stackNew.contains(edge));

                stackNew.push(edge);

            }
            ptr = rootState;
        }
        return null;
    }

    /**
     * 用于获取一条路径的XML
     * @param stackNew 路径栈
     * @return XML格式的String
     */
    private String getXML(Stack<Edge> stackNew){
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
    private boolean isNewPath(String XML,List<String> pathMemory){
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
    private State findStateByName(List<State> appStates,String stateName){
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
