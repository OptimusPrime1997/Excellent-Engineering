import impl.area.*;
import impl.edge.Operation;
import impl.edge.VirtualOperation;
import impl.model.ModelFactory;
import impl.state.State;
import util.xmlReader.XMLReader;
import util.xmlWriter.PathStrategyEnum;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import service.Area;
import service.Edge;
import service.ModelService;
import util.Action;
import util.StringUtil;
import util.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王栋 on 2016/7/11 0011.
 */
public class ReadModelTest {
    private Document document;
    @Test
    public void readXML(){
        document = XMLReader.readXML(ReadModelTest.class.getClassLoader().getResource("stub.xml").getFile());
        System.out.println(document);

        Document doc= DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("paths");
        doc.setRootElement(root);
    }

    @Test
    public void readFile(){
        try {
            FileInputStream inputStream = new FileInputStream(ReadModelTest.class.getClassLoader().getResource("stub.xml").getFile());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String buf = null;
            while((buf=reader.readLine())!=null){
                System.out.println(buf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModel(){
        document = XMLReader.readXML(ReadModelTest.class.getClassLoader().getResource("stub.xml").getFile());
        System.out.println(document);

        //先根遍历
        ModelService model= ModelFactory.getModelInstance(PathStrategyEnum.ROOT_FRIST);
        List<Element> stateElements = document.getRootElement().elements("state");
        List<Element> operationElements = document.getRootElement().elements("operation");
        List<State> states = new ArrayList<State>(stateElements.size());
        List<Operation> operations = new ArrayList<Operation>(operationElements.size());
        //读取所有的状态集
        for(int i = 0 ; i < stateElements.size() ;i++){
            Element elem = stateElements.get(i);
            String elemName = elem.getName();

                    String name = elem.elementText("fileName");
                    int id = Integer.parseInt(elem.elementText("stateId"));
                    if(elem.attributeValue("typeCode").equals("1")&&i!=0){
                        states.set(i, new State(name+id,id));

                    }else if(elem.attribute("typeCode").equals("1")&&i==0){
                        states.set(i,new State(elem.elementText("fileName"),Integer.parseInt(elem.elementText("stateId"))));
                        model.setRoot(states.get(i).getName(),states.get(i).getId());//设置根节点
                    }else {//是一个oracle

                }
        }

        //读取所有的中间边 并将其加到边上面
        for(int i = 0 ; i < operationElements.size() ; i++ ){
            State start = states.get(i);
            State next = states.get(i+1);
            Element elem = operationElements.get(i);
            Edge operation = getOperation(start.getName(), next.getName(),elem);
            start.addEdge(operation);
            model.addEdge(start.getId(),next.getId(),operation);
        }
    }


    private Edge getOperation(String start,String next ,Element element){
        if(element.attributeValue(StringUtil.isVirtual)!=null&&element.attributeValue(StringUtil.isVirtual).equalsIgnoreCase("true")){
            return new VirtualOperation();
        }

        Action action = Action.valueOf(element.attributeValue(StringUtil.action).toUpperCase());
        Type type = Type.valueOf(element.attributeValue(StringUtil.type).toUpperCase());
        int delay = Integer.parseInt(element.attributeValue(StringUtil.delay));
        String id = element.attributeValue(StringUtil.id);
//
//        this.action = action;
//        this.destination = destination;
//        this.resources = resources;
//        this.area = area;
//        this.id = id;
//        this.searchTimes = 0;
//        this.delay=delay;
        Area area = getArea(type,element);
        Operation operation = new Operation(action,next,start,area,id,delay);


        return operation;
    }


    private Area getArea(Type type,Element operation){
        Area result = null;

        if(type==Type.COMPONENT){
            result = new Component(operation.elementText(StringUtil.resourceId));
        }else if(type==Type.AREA){
            List<Element> elements = operation.element(StringUtil.doublePoint).elements();
            Element element1 = elements.get(0);
            Element element2 = elements.get(1);
            SinglePoint point1 = new SinglePoint(Float.parseFloat(element1.elementText(StringUtil.pointX)),
                                                Float.parseFloat(element1.elementText(StringUtil.pointY)));
            SinglePoint point2 = new SinglePoint(Float.parseFloat(element2.elementText(StringUtil.pointX)),
                    Float.parseFloat(element2.elementText(StringUtil.pointY)));
            result = new Range(point1,point2);
        }else if(type==Type.DOUBLE_POINT){
            List<Element> elements = operation.element(StringUtil.doublePoint).elements();
            Element element1 = elements.get(0);
            Element element2 = elements.get(1);
            SinglePoint point1 = new SinglePoint(Float.parseFloat(element1.elementText(StringUtil.pointX)),
                    Float.parseFloat(element1.elementText(StringUtil.pointY)));
            SinglePoint point2 = new SinglePoint(Float.parseFloat(element2.elementText(StringUtil.pointX)),
                    Float.parseFloat(element2.elementText(StringUtil.pointY)));
            result = new DoublePoint(point1,point2);
        }else if(type==Type.POINT_TO_AREA){
            Element element3 = operation.element(StringUtil.singlePoint);
            SinglePoint point3 = new SinglePoint(Float.parseFloat(StringUtil.pointX),Float.parseFloat(StringUtil.pointY));

            List<Element> elements = operation.element(StringUtil.doublePoint).elements();
            Element element1 = elements.get(0);
            Element element2 = elements.get(1);
            SinglePoint point1 = new SinglePoint(Float.parseFloat(element1.elementText(StringUtil.pointX)),
                    Float.parseFloat(element1.elementText(StringUtil.pointY)));
            SinglePoint point2 = new SinglePoint(Float.parseFloat(element2.elementText(StringUtil.pointX)),
                    Float.parseFloat(element2.elementText(StringUtil.pointY)));
            result = new DragRange(point1,point2,point3);
        }else if(type==Type.SINGLE_POINT){
            Element element = operation.element(StringUtil.singlePoint);

            result = new SinglePoint(Float.parseFloat(element.elementText(StringUtil.pointX)),Float.parseFloat(StringUtil.pointY));
        }
        return result;
    }
}
