import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;
import util.xmlReader.XMLReader;

import javax.print.Doc;
import java.util.List;

/**
 * Created by 王栋 on 2016/7/18 0018.
 */
public class Test0Tag {

    @Test
    public void test0Tag(){
        Document document = XMLReader.readXML(Test0Tag.class.getClassLoader().getResource("test.xml").getFile());
        if(document.getRootElement().getName().equals("edges")){
            List<Element> elements = document.getRootElement().elements();
            System.out.println(elements.size());
        }
    }

    @Test
    public void test0Tag2(){
        Document document = XMLReader.readXML(Test0Tag.class.getClassLoader().getResource("test.xml").getFile());
        Element elem = document.getRootElement().element("not");
        if(elem==null){
            System.out.println(true);
        }
    }
}
