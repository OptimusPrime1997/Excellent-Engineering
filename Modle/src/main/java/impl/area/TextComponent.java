package impl.area;

/**
 * Created by 王栋 on 2016/7/22 0022.
 */
public class TextComponent extends Component {
    private String input;

    public TextComponent(String id, String type, String message ,String content) {
        super(id, type,message);
        this.input = content;
    }

    public String toXML() {
        String XML = "<textComponent>\n" +
                "<index>" + id + "</index>\n"+
                "<componentType>"+ type + "</componentType>\n" +
                "<input>"+input+"</input>\n"+
                "<message>"+message+"</message>\n"+
                "</textComponent>\n";

        return XML;
    }

    public String printArea() {
        String print = "<area  type = \"textComponent\">\n" +
                "<type>" + type + "</type>\n" +
                "<id>"+id+"</id>\n" +
                "<input>"+input+"</input>\n"+
                "<message>"+message+"</message>\n"+
                "</area>\n";
        return print;
    }
}
