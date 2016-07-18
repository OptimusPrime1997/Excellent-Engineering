package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/18.
 */
public class NotOperation implements Result {
    private Result result;

    public NotOperation(Result result) {
        this.result = result;
    }

    public String toXML() {
        String XML = "<not>\n" +
                     result.toXML() +
                     "</not>\n";
        return XML;
    }

    public String printResult() {
        return this.toXML();
    }
}
