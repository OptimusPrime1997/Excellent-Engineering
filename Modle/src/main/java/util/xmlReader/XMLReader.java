package util.xmlReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by 王栋 on 2016/7/11 0011.
 */
public class XMLReader {

    /**
     * 读取XML的工具类 用于获取目标xml文件的结构Document
     * @param fileName 所要读取的目标xml文件的目录字符串
     * @return 返回xml对应的document
     */
    public static Document readXML(String fileName){
        SAXReader saxReader = new SAXReader();
        Document document = null;

        try {
            document = saxReader.read(new File(fileName));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }


}
