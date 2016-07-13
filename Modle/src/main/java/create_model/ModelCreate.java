package create_model;


import impl.area.*;
import impl.edge.Operation;
import impl.edge.VirtualOperation;
import impl.model.ModelFactory;
import impl.state.State;
import org.dom4j.Document;
import org.dom4j.Element;
import service.Area;
import service.Edge;
import service.ModelService;
import util.Action;
import util.StringUtil;
import util.Type;
import util.xmlReader.XMLReader;
import util.xmlwriter.PathStrategyEnum;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王栋 on 2016/7/13 0013.
 */
public class ModelCreate {

    private ModelService model;

    public ModelService getModel(PathStrategyEnum type, File[] xmlFiles){
        model = ModelFactory.getModelInstance(type);

        for (File file : xmlFiles){
            anaXml(file);
        }

        return model;
    }


    private void anaXml(File file){
       Document document =  XMLReader.readXML(file.getPath());
        List<Element> stateElements = document.getRootElement().elements("state");
        List<Element> operationElements = document.getRootElement().elements("operation");
        List<State> states = new ArrayList<State>(stateElements.size());
        List<Edge> operations = new ArrayList<Edge>(operationElements.size());
        //读取所有的状态集
        for(int i = 0 ; i < stateElements.size() ;i++){
            Element elem = stateElements.get(i);
            //String elemName = elem.getName();

            String name = elem.elementText(StringUtil.fileName);
            int id = Integer.parseInt(elem.elementText(StringUtil.stateId));
            if(elem.attributeValue(StringUtil.typeCode).equals("1")&&i!=0){
                states.set(i, new State(name+id,id));

            }else if(elem.attribute(StringUtil.typeCode).equals("1")&&i==0){
                states.set(i,new State(elem.elementText(StringUtil.fileName),Integer.parseInt(elem.elementText("stateId"))));
                model.setRoot(states.get(i).getName(),states.get(i).getId());//设置根节点
            }else {//是一个oracle
                //TODO 分析类型产生对应的oracle
                
            }


        }

        //读取所有的中间边 并将其加到边上面
        for(int i = 0 ; i < operationElements.size() ; i++ ){
            State start = states.get(i);
            State next = states.get(i+1);
            Element elem = operationElements.get(i);
            Edge operation = getOperation(start.getName(), next.getName(),elem);
            start.addEdge(operation);
            operations.set(i,operation);
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

        Area area = getArea(type,element);
        Operation operation = new Operation(action,next,start,area,id,delay);


        return operation;
    }

    private Area getArea(Type type,Element operation){
        Area result = null;

        if(type==Type.COMPONENT){
            result = new Component(operation.elementText(StringUtil.resourceId),operation.elementText(StringUtil.resourceType));
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
