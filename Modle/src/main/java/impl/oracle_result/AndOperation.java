package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/18.
 */
public class AndOperation implements Result {
    private Result result1;
    private Result result2;

    public AndOperation(Result result1, Result result2) {
        this.result1 = result1;
        this.result2 = result2;
    }

    public String toXML() {
        String XML = "<and>\n" +
                result1.toXML() +
                result2.toXML() +
                "</and>\n";
        return XML;
    }

    public String printResult() {
        return this.toXML();
    }

    public void setResult1(Result result1) {
        this.result1 = result1;
    }

    public void setResult2(Result result2) {
        this.result2 = result2;
    }
}
