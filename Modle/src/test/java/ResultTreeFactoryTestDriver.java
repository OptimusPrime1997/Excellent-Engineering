import impl.oracle_result.Result;
import impl.oracle_result.ResultTreeFactory;
import impl.oracle_result.ResultType;
import impl.oracle_result.SingleComponentResult;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ResultTreeFactoryTestDriver {

    public static void main(String args[]){
        SingleComponentResult sr1 = new SingleComponentResult("1", ResultType.IMAGE,"Button","abc","");
        SingleComponentResult sr2 = new SingleComponentResult("2", ResultType.IMAGE,"Button","abc","");
        SingleComponentResult sr3 = new SingleComponentResult("3", ResultType.IMAGE,"Button","abc","");
        SingleComponentResult sr4 = new SingleComponentResult("4", ResultType.IMAGE,"Button","abc","");
        ResultTreeFactory rf = new ResultTreeFactory();
        rf.put("not");
        rf.put(sr1);
        rf.put("and");
        rf.put("(");
        rf.put(sr2);
        rf.put("and");
        rf.put(sr3);
        rf.put("or");
        rf.put(sr4);
        rf.put(")");
        Result rs = rf.createResultTree();
        System.out.println(rs.toXML());
    }
}
