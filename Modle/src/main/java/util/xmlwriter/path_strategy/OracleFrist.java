package util.xmlWriter.path_strategy;

import util.xmlWriter.PathStrategy;

/**
 * Created by Administrator on 2016/7/11.
 */
public class OracleFrist extends PathStrategy {

//    private final int LOOP_LIMIT = 3;
//    @Override
//    public void writeXML(List<State> appStates,String root,int times){
//        /*
//            找到根节点
//         */
//        State rootState = this.findStateByName(appStates,root);
//        String XML = this.findTestPath(appStates,rootState,times);
//    }
//
//    /**
//     * 该方法用于寻找测试的路径，并生成XML格式的字符串，该方法不成熟，有待完善
//     * 该方法遍历的时候采用的是循环而非递归
//     * @param appStates app的状态集,由于是从Oracle开始寻路的，所以appStates应当是逆邻接链表
//     * @param rootState 根节点
//     * @param times 测试次数
//     * @return XML格式的关于路径的字符串
//     */
//    private String findTestPath(List<State> appStates,State rootState,int times){
//        /*
//           一个用于存储所有的Oracle的List，把Oracle看成遍历的根节点，这个List只保存有路径为遍历的Oracle
//         */
//        List<State> oracleList = new ArrayList<State>();
//        List<String> XMLRecord = new ArrayList<String>();   //用于保存XML格式的String
//        /*
//            找到所有的Oracle
//         */
//        for(State state : appStates){
//            if(state.isOracle()){
//                oracleList.add(state);
//            }
//        }
//
//        while(times > 0 && oracleList.size() > 0){
//            /*
//                下面两句是用来为每个Oracle设定数目相似的测试路径数目
//             */
//            int average = times / oracleList.size();
//            times = times - average * oracleList.size();
//            if(average == 0){  //如果要求找到的测试条数小于Oracle的数目，就随机选取Oracle
//                while(times > 0 && oracleList.size() > 0){
//                    String XML = null;
//                    State state = null;
//                    /*
//                        这里XML等于Null说明这个Oracle遍历完了之后都没有找到新的测试路径，那么就一直选取，知道找到一个为止,
//                        并把遍历完的state从oracleList里面删除
//                     */
//                    while(oracleList.size() > 0){
//                        int index = (int) (Math.random() * oracleList.size());
//                        state = oracleList.get(index);
//                        XML = this.searchSingleOraclePath(state,rootState,appStates);
//                        if(XML == null){
//                            oracleList.remove(index);
//                        }
//
//                    }
//
//                    if(XML != null){  //如果所有的Oracle都找不到路径了，就不添加了，找到了才添加
//                        XMLRecord.add(XML);
//                    }
//
//                }
//            }else{ //否则，则依次均匀选取Oracle
//                List<State> memory = new ArrayList<State>();
//                for( State state : oracleList){
//                    boolean isSearchComplete = false;
//                    for(int counter = 0;counter < average; counter ++){
//                        String XML = this.searchSingleOraclePath(state,rootState,appStates);
//                        if(XML != null){
//                            XMLRecord.add(XML);
//                        }else{
//                            isSearchComplete = true;
//                            break;
//                        }
//
//                    }
//                    if(!isSearchComplete){  //如果这个Oracle中的所有的路径已经遍历完，那么把这个oracle从oracleList中移除
//                        /*
//                            由于用力迭代器，所以我们转换思维，将没有遍历完的Oracle存到另外一个链表里面
//                            在一轮循环完成之后，赋值给oracleList
//                         */
//                        memory.add(state);
//                    }
//                }
//                oracleList = memory;
//            }
//
//        }
//        String result = "";
//        for(String str : XMLRecord){
//            result += str;
//        }
//        return result;
//    }
//
//    /**
//     * 用于搜索一个Oracle到root的测试路径
//     * @param oracle
//     * @param root
//     * @param appStates
//     * @return
//     */
//    private String searchSingleOraclePath(State oracle,State root,List<State> appStates){
//        State ptr = oracle;
//        //Iterator<Edge> it = ptr.iterator();
//        Stack<Edge> edgeStack = new Stack<Edge>();
//        Edge edge = null;
//
//        while(ptr != root && !isSearchComplete(oracle,appStates)){    //这里的ptr != root是可行的
//            int stackDeepMemory = edgeStack.size();
//            int loopTimes = 0;
//            /*
//            下面的While循环用于寻找一个未被遍历的路径
//            */
//            //-----------------------------------------------------------------------
//            List<Integer> random = new ArrayList<Integer>();
//            while(ptr.length() >= 0){ //防止叶子节点进入循环
//
//                for(int x = 0 ; x < ptr.length() ; x++){
//                    random.add(x);
//                }
//                int index = (int) (Math.random() * random.size());
//                edge = ptr.get(random.get(index));  //随机选取一条路径
//                if(!edge.isHasSearchComplete()){
//                    loopTimes = this.getLoopTimes(edge,edgeStack);
//                    edgeStack.push(edge);
//                    ptr = this.findStateByName(appStates,edge.getRescoures());  //由于这个是逆邻接表，边的Rescoures端反而是子节点
//
//                    break;
//                }
//                random.remove(index);
//            }
//            //-----------------------------------------------------------------------
//            /*
//                如果没有找到一条可以添加的边,或者环路超过允许次数，就折返到父节点重新选
//             */
//            if(stackDeepMemory == edgeStack.size() || loopTimes >= LOOP_LIMIT){
//                edge = edgeStack.pop();
//                ptr = this.findStateByName(appStates,edge.getDestination());
//                edge.searchComplete();  //将edge设置为已经遍历过了
//            }
//        }
//        if(edgeStack.empty()){
//            return null;
//        }
//        String XML = "";
//        while(!edgeStack.empty()){
//            XML += edgeStack.pop().toXML();
//        }
//        return XML;
//    }
//
//    private int getLoopTimes(Edge edge,Stack<Edge> edgeStack){
//        int loopTimes = 0;
//        for(Edge ed : edgeStack){
//            if(ed == edge){
//                loopTimes ++;
//            }
//        }
//        return loopTimes;
//    }
//
//    /**
//     * 用于检查一个Oracle是否遍历完成
//     * @param oracle
//     * @param appStates
//     * @return
//     */
//    private boolean isSearchComplete(State oracle,List<State> appStates){
//        Iterator<Edge> it =oracle.iterator();
//        while(it.hasNext()){
//            Edge edge = it.next();
//            /*
//                如果这条边所指向的节点还有边没有遍历那么返回false
//             */
//            if(!this.hasUnSearchedEdge(edge,appStates)){
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean hasUnSearchedEdge(Edge edge,List<State> appStates){
//        State state = this.findStateByName(appStates,edge.getRescoures());
//        if(state.length() == 0){  //如果链接的是叶子节点，那么肯定遍历完了
//            edge.isHasSearchComplete();
//            return true;
//        }
//        if(edge.isHasSearchComplete()){ //如果edge中记录的边的指针已经超过了节点的边的下标的上界，那么也遍历完了
//            return true;
//        }
//        return false;
//    }

}
