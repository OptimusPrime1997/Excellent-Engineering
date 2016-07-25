package create_model;


import impl.area.*;
import impl.edge.Operation;
import impl.edge.VirtualOperation;
import impl.model.ModelFactory;
import impl.oracle_result.*;
import impl.state.Oracle;
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
public class ModelCreateByPaths {

    private ModelService model;

    public ModelService getModel(PathStrategyEnum type, File[] xmlFiles,String savePath){
        model = ModelFactory.getModelInstance(type,savePath);

        for (File file : xmlFiles){
            System.err.print("test_"+file.getName());
            anaXml(file);
        }

        return model;
    }


    private void anaXml(File file){
       Document document =  XMLReader.readXML(file.getPath());
        List<Element> stateElements = document.getRootElement().elements(StringUtil.state);
        List<Element> operationElements = document.getRootElement().elements(StringUtil.operation);
        List<State> states = new ArrayList<State>(stateElements.size());
        List<Edge> operations = new ArrayList<Edge>(operationElements.size());

        /*****/
        Oracle oracle = null;
        Result result = null;
        /****/
        //读取所有的状态集
        for(int i = 0 ; i < stateElements.size() ;i++){
            Element elem = stateElements.get(i);
            //String elemName = elem.getName();

            String name = elem.elementText(StringUtil.fileName);
            int id = Integer.parseInt(elem.elementText(StringUtil.stateId));

//            System.out.println(elem.attributeValue(StringUtil.typeCode));
//            System.out.println(elem.attributeValue(StringUtil.typeCode).equals("1"));
            if(elem.attributeValue(StringUtil.typeCode).equals("1")){
                if(i!=0)
                states.add(i, new State(name+id,id));
                else {
                    id=0;
                    states.add(i,new State(name+id,id));
                    model.setRoot(name,id);//设置根节点
                }

            }else{//是一个oracle
                //TODO 分析类型产生对应的oracle

//                String type = elem.attributeValue(StringUtil.type);
//                if(type.equalsIgnoreCase("single_component")){
//                    Element element = elem.element(StringUtil.singleComponent);
//                    result = getSingleComponent(element);
//
//                }else if(type.equalsIgnoreCase("multi_component")){
//                    //TODO 要测试多个组件的内容或者图片 估计都不到这里了 都被not or and 和 multiComponent包含了
////                    List<Element> elements = elem.element(StringUtil.multiComponent).elements(StringUtil.singleComponent);
////                    result = new MultiComponentResult();
////                    for(Element element : elements){
////                        (MultiComponentResult)result.
////                    }
//                }

                result = getResult(elem);
                oracle = new Oracle(name+id,id,result);
                states.add(i,oracle);
                //model.setOracle
            }

        }

        System.out.println(operationElements.size());
        //读取所有的中间边 并将其加到边上面
        for(int i = 0 ; i < operationElements.size() ; i++ ){
            State start = states.get(i);
            State next = states.get(i+1);
            Element elem = operationElements.get(i);
            Edge operation = getOperation(start.getName(), next.getName(),elem);
            start.addEdge(operation);
            operations.add(i,operation);//WTF
            model.addEdge(start.getId(),next.getId(),operation);
//            System.out.println(i);
        }

        model.setOracle(oracle.getName(),oracle.getId(),result);

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
            result = new Component(operation.elementText(StringUtil.resourceId),operation.elementText(StringUtil.resourceType),operation.elementText(StringUtil.message));
        }else if (type==Type.TEXTCOMPONENT){//输入框
            result = new TextComponent(operation.elementText(StringUtil.resourceId),operation.elementText(StringUtil.resourceType),operation.elementText(StringUtil.message),operation.elementText(StringUtil.input));

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
            SinglePoint point3 = new SinglePoint(Float.parseFloat(element3.elementText(StringUtil.pointX)),Float.parseFloat(element3.elementText(StringUtil.pointY)));

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

            result = new SinglePoint(Float.parseFloat(element.elementText(StringUtil.pointX)),Float.parseFloat(element.elementText(StringUtil.pointY)));
        }else if(type==Type.MULTICOMPONENT){
            MultiComponent multiComponent = new MultiComponent();
            List<Element> elements = operation.element(StringUtil.multiComponent).elements();
            for(Element element:elements){
                Component component = new Component(element.elementText(StringUtil.resourceId),
                        element.elementText(StringUtil.resourceType),element.elementText(StringUtil.message));
                multiComponent.addComponent(component);

            }

            result = multiComponent;
        }
        return result;
    }


    //TODO 分析result 获取result
    private Result getResult(Element elem){
        ResultTreeFactory result = new ResultTreeFactory();
        //如果不包含与或非操作
        if(elem.element(StringUtil.or)==null&&elem.element(StringUtil.not)==null&&elem.element(StringUtil.and)==null){
            return getSingleComponent(elem.element(StringUtil.singleComponent));
        }

        List<Element> elements = elem.elements();
        for(Element element : elements){
            String name = element.getName();
            if(name.equalsIgnoreCase(StringUtil.stateId)||element.getName().equalsIgnoreCase(StringUtil.fileName))
                continue;

            if(name.equals(StringUtil.or)||name.equals(StringUtil.and)||name.equals(StringUtil.not)){
//                System.err.println("test1");
                result.put(name);
            }else if(name.equals(StringUtil.rightBrack)){
//                System.err.println("test2");
                result.put(")");
            }else if(name.equals(StringUtil.leftBrack)){
//                System.err.println("test3");
                result.put("(");
            }else if(name.equals(StringUtil.singleComponent)){
//                System.err.println("test4");
                result.put(getSingleComponent(element));
            }
        }
        return result.createResultTree();
    }


    private Result getSingleComponent(Element elem){
        Result result = null;
        ResultType resultType = getResultType(elem.element(StringUtil.expect).attributeValue(StringUtil.type));
        if(resultType == ResultType.TEXT){
        String componentType = elem.elementText(StringUtil.resourceType);
        String index = elem.elementText(StringUtil.resourceId);
        String expect = elem.elementText(StringUtil.expect);
        String message = elem.elementText(StringUtil.message);
        result = new SingleComponentResult(index,resultType,componentType,expect,message);
        }else if (resultType==ResultType.IMAGE){
            Element singlePoint = elem.element(StringUtil.expect).element(StringUtil.singlePoint);
            Element color = elem.element(StringUtil.expect).element(StringUtil.color);
            float x = Float.parseFloat(singlePoint.elementText(StringUtil.pointX));
            float y = Float.parseFloat(singlePoint.elementText(StringUtil.pointY));
            String colorStr = color.getText();
            result = new PixelsResult(colorStr,x,y);

        }
        return result;
    }


    private ResultType getResultType(String type){
        type = type.toUpperCase();

        return ResultType.valueOf(type);

    }

}
