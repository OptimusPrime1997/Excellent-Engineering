package impl.state;

import service.Edge;

import java.util.List;
import impl.oracle_result.Result;

/**
 * Created by 王栋 on 2016/7/11 0011.
 */
public class Oracle extends State{
    private Result result;

    public Oracle(String name, int id, Result result) {
        super(name,id);
        this.result = result;
    }

    public Oracle(String name, int id, Result result,List<Edge> edges){
        super(name,id,edges);
        this.result = result;
    }

    @Override
    public boolean isOracle(){
        return true;
    }

    public String toXML(){
        String XML = "<oracle type = \"" + result.getClass().getName() + "\">\n"
                   + result.toXML()
                   + "</oracle>\n";
        return XML;
    }

    @Override
    public String printState(){
        String print = "<oracle>\n" +
                "<result>\n"+
                result.printResult()+
                "</result>\n" +
                "<name>"+this.name+"</name>\n"+
                "<id>"+this.id+"</id>\n" +
                "<edges>\n";
        for(Edge edge : edgeList){
            print += edge.printEdge();
        }
        print += "</edges>\n"+
                "</oracle>\n";
        return print;
    }
}
