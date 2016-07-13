package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/13.
 */
public class SingleComponentResult implements Result{

    private int index;
    private ResultType resultType;
    private String expect;

    public String toXML() {
        String XML = "<singleComponent>"
                   + "<index>" + index + "</index>\n"
                   + "<resultType>" + resultType + "</resultType>\n"
                   + "<expect>" + expect + "</expect>"
                   + "</singleComponent>";

        return XML;
    }

}
