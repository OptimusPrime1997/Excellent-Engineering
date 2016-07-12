package impl.xmlwriter.path_strategy;

import impl.Oracle;
import impl.State;
import impl.xmlwriter.PathStrategy;
import service.Edge;

import java.io.FileWriter;
import java.util.*;

/**
 * Created by Administrator on 2016/7/11.
 */
public class RootFrist extends PathStrategy {

    private final int LOOP_LIMIT = 3;
    private final int DEEP_LIMIT = 15;

    @Override
    public void writeXML(List<State> appStates, String root, int times){
        String XML = this.findTestPath(appStates,this.findStateByName(appStates,root),times);
        try{
            FileWriter fileWriter=new FileWriter("paths.xml");
            fileWriter.write(XML);
            fileWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private String findTestPath(List<State> appStates, State rootState, int times) {
        State ptr = rootState;
        int search_limit = times * 2;
        List<String> XMLRecord = new ArrayList<String>();
        for(int counter = 0 ; counter < search_limit && XMLRecord.size() < times; counter ++){

            int loop_limit = 0;
            int deep_limit = 0;
            List<Edge> pathRecord =new LinkedList<Edge>();
            /**
             * while的结束条件是，遇到只有出度为0的节点退出循环，陷入环路次数过多时退出循环，探索图的深度过深的时候结束循环
             */
            while(ptr.length() > 0 && loop_limit < LOOP_LIMIT && deep_limit < DEEP_LIMIT){
                loop_limit = 0;
                Edge edge = this.getMinEdge(ptr);
                edge.addSearchTimes();  //边的访问次数加一
                for(Edge e : pathRecord){   //检查环路
                    if(e == edge){
                        loop_limit ++;
                    }
                }
                pathRecord.add(edge);
                ptr = this.findStateByName(appStates,edge.getDestination());
                if(ptr.isOracle()){
                    XMLRecord.add(createPathXML(pathRecord,(Oracle) ptr));  //由于ptr已经确认是Oracle了，直接强制转换
                }
                deep_limit ++;
            }
            ptr = rootState;

        }
        String result = "<paths>";
        for(String str : XMLRecord){
            result += str;
        }
        result += "</paths> \n";
        return result;
    }

    /**
     * 用于生成一条路径的XML
     * @param edges 路径的记录数组
     * @return XML格式的字符串
     */
    private String createPathXML(List<Edge> edges, Oracle oracle){
        String XML = "<path> \n";
        for(Edge edge : edges){
            XML += edge.toXML();
        }
        XML += oracle.toXML();
        XML += "</path> \n";
        return XML;
    }
    /**
     * 用于找出一个状态中访问次数最少的边
     * @param state 状态
     * @return 访问次数最少的随机一条边
     */
    private Edge getMinEdge(State state){
        if(state.length() == 0){
            return null;
        }
        Iterator<Edge> it = state.iterator();
        int min = it.next().getSearchTimes();
        while(it.hasNext()){
            int next = it.next().getSearchTimes();
            if(min > next){
                min = next;
            }
        }
        List<Edge> selectList = new ArrayList<Edge>();
        it = state.iterator();
        while(it.hasNext()){
            Edge e = it.next();
            if(e.getSearchTimes() == min){
                selectList.add(e);
            }
        }
        int index = (int) (selectList.size() * Math.random());
        return state.get(index);

    }
}
