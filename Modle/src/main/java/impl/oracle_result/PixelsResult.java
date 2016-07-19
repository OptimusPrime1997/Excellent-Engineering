package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/18.
 */
public class PixelsResult implements Result {
    private int[] RGB;
    private int Xray;
    private int Yray;

    public PixelsResult(int[] RGB , int Xray , int Yray) {
        this.RGB = RGB;
        this.Xray = Xray;
        this.Yray = Yray;
    }

    public String toXML() {
        String XML = "<pixelsResult>\n" +
                "<r>" + RGB[0] + "</r>\n" +
                "<g>" + RGB[1] + "</g>\n" +
                "<b>" + RGB[2] + "</b>\n" +
                "<Xray>" + Xray + "</Xray>\n" +
                "<Yray>" + Yray + "</Yray>\n" +
                "</pixelsResult>\n";
        return XML;
    }

    public String printResult() {
        return this.toXML();
    }
}
