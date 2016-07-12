import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;

/**
 * Created by 王栋 on 2016/7/12 0012.
 */
public class testDom4j {

    @Test
    public void test1() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(testDom4j.class.getClassLoader().getResource("stub.xml").getFile()));
        Element root = doc.getRootElement();
        System.out.println(root.attributeValue("eheh"));
    }

    @Test
    public void test2() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(testDom4j.class.getClassLoader().getResource("stub.xml").getFile()));
        Element root = doc.getRootElement();
    }
}
