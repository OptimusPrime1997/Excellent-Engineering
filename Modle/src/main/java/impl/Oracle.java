package impl;
import service.Edge;
import java.util.List;

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
        return result.toXML();
    }
}
