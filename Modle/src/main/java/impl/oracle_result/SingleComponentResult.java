package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/13.
 */
public class SingleComponentResult implements Result{

    private String index;
    private ResultType resultType;
    private String componentType;
    private String expect;
    private String message;

    public String toXML() {
        String XML = "<singleComponent>\n"
                   + "<index>" + index + "</index>\n"
                   +"<message>"+message+"</message>\n"
                   + "<resultType>" + resultType.name() + "</resultType>\n"
                   + "<componentType>" + componentType + "</componentType>\n"
                   + "<expect>" + expect + "</expect>\n"
                   + "</singleComponent>\n";

        return XML;
    }

    public String printResult() {
        return this.toXML();
    }

    public SingleComponentResult(String index, ResultType resultType, String componentType, String expect,String message) {
        this.index = index;
        this.resultType = resultType;
        this.componentType = componentType;
        this.expect = expect;
        this.message = message;
    }
}
