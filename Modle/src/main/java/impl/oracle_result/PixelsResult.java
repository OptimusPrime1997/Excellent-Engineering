package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/18.
 */
public class PixelsResult implements Result {
    private String color;
    private float Xray;
    private float Yray;

    public PixelsResult(String color , float Xray , float Yray) {
        this.color = color;
        this.Xray = Xray;
        this.Yray = Yray;
    }

    public String toXML() {
        String XML = "<pixelsResult>\n" +
                "<color>" + color + "</color>\n" +
                "<Xray>" + Xray + "</Xray>\n" +
                "<Yray>" + Yray + "</Yray>\n" +
                "</pixelsResult>\n";
        return XML;
    }

    public String printResult() {
        return this.toXML();
    }
}
