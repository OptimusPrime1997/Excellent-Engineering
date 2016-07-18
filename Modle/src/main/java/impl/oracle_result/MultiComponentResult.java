package impl.oracle_result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MultiComponentResult implements Result {

    private List<SingleComponentResult> list = new ArrayList<SingleComponentResult>();


    public String toXML(){
        String  XML= "";
        for(SingleComponentResult scr : list){
            XML += scr.toXML();
        }
        return XML;
    }

    public String printResult() {
        return this.toXML();
    }

    public void add(SingleComponentResult scr){
        list.add(scr);
    }
}
