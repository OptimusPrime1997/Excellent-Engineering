package util.xmlwriter.path_strategy;

import impl.state.Oracle;
import impl.state.State;
import util.xmlwriter.PathStrategy;
import service.Edge;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/7/11.
 */
public class RootFrist extends PathStrategy {

    private final int LOOP_LIMIT = 3;
    private final int DEEP_LIMIT = 15;

    @Override
    public void writeXML(List<State> appStates, String root, int times,String savePath){
        String XML = this.findTestPath(appStates,this.findStateByName(appStates,root),times);
        XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + XML;
        try{
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD-HH-MM-SS");
            File file = new File(savePath);

            if(!file.getParentFile().exists()) {
                System.err.println("making the parent file ...");
                if(!file.getParentFile().mkdirs()) {
                    System.err.println("parent file making failed!");
                }
            }

            FileWriter fileWriter=new FileWriter(file);
            fileWriter.write(XML);
            fileWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String findTestPath(List<State> appStates, State rootState, int times) {
        State ptr = rootState;
        if(rootState == null){
            System.err.println("Invalid root");
        }
        int search_limit = times * 2;
        List<String> XMLRecord = new ArrayList<String>();
        //System.out.println("times : "+ times);
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
                        System.out.println("find a loop");
                        loop_limit ++;
                    }
                }
                pathRecord.add(edge);
                ptr = this.findStateByName(appStates,edge.getDestination());
                if(ptr.isOracle()){
                    System.out.println("find oracle :) ");
                    String xml =createPathXML(pathRecord,(Oracle) ptr); //由于ptr已经确认是Oracle了，直接强制转换
                    if(!XMLRecord.contains(xml)){
                        System.out.println("new path :)");
                        XMLRecord.add(xml);
                    }else{
                        System.out.println("path already exist :(");
                    }
                }
                deep_limit ++;
            }
            ptr = rootState;
            System.out.println("looping...");
        }
        String result = "<paths>\n";
        for(String str : XMLRecord){
            result += str;
        }
        result += "</paths>\n";
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
        System.out.println("Function getMinEdge called!!!");
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
        System.out.println("Function getMinEdge finished!!!");
        System.out.println("Candidate num : "+selectList.size());
        System.out.println(index);
        return selectList.get(index);

    }


}
