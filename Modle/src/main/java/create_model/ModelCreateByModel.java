package create_model;

import com.sun.org.apache.regexp.internal.REUtil;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import impl.area.*;
import impl.edge.Operation;
import impl.edge.VirtualOperation;
import impl.model.Model;
import impl.oracle_result.*;
import impl.state.Oracle;
import impl.state.State;
import org.dom4j.Document;
import org.dom4j.Element;
import service.Area;
import service.Edge;
import util.Action;
import util.StringUtil;
import util.xmlReader.XMLReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 王栋 on 2016/7/17 0017.
 */
public class ModelCreateByModel {

    private List<State> states;
    private String rootName;
    private int id;

    public Model getModel(File file){
        states = new ArrayList<State>();
        Document document = XMLReader.readXML(file.getPath());
        Element root = document.getRootElement();
        List<Element> stateElems = root.elements(StringUtil.state);
        List<Element> oracleElems = root.elements(StringUtil.oracle);
        rootName = stateElems.get(0).elementText(StringUtil.name);
        id = Integer.parseInt(stateElems.get(0).elementText(StringUtil.id));
        for(Element element : stateElems){
            states.add(getState(element));
        }

        for(Element element:oracleElems){
            states.add(getOracle(element));
        }

        Model model = new Model(states,rootName,id);
        String path = file.getParentFile().getPath();
        model.setSavePath(path + File.separator);
        return model;
    }

    //将state的标签传入，生成一个state的对象
    private State getState(Element element){
        String name = element.elementText(StringUtil.name);
        int id = Integer.parseInt(element.elementText(StringUtil.id));
        State state = new State(name,id);

        List<Element> elements = element.element(StringUtil.edges).elements(StringUtil.edge);

        //TODO 加边
        for(Element elem : elements){
            state.addEdge(getEdge(elem));
        }

        return state;
    }


    //将标签为oracle的标签传入 生成一个oracle的对象
    private Oracle getOracle(Element element){
        String name = element.elementText(StringUtil.name);
        int id = Integer.parseInt(element.elementText(StringUtil.id));
        //每一个Oracle必定是有一个result的
        List<Element> elems = element.element(StringUtil.result).elements();
        Element resultContent = elems.get(0);
        Result result = getResult(resultContent);
        Oracle oracle = new Oracle(name,id,result);


        List<Element> elements = element.element(StringUtil.edges).elements(StringUtil.edge);

        //TODO 加边
        for(Element elem : elements){
            oracle.addEdge(getEdge(elem));
        }


        return oracle;
    }


    //TODO 完善这个方法 使用递归的方法实现 与或非的结果
    //TODO 将标签为Result的标签传入生成一个result对象
    // 暂时觉得使用递归比较方便  但是可能会造成内存泄漏栈溢出等问题
    private Result getResult(Element elem){

        Result result = null;

            String name = elem.getName();
            if(name.equalsIgnoreCase(StringUtil.singleComponent)){
                String index = elem.elementText(StringUtil.resourceId);
                ResultType resultType = ResultType.valueOf(elem.elementText(StringUtil.resultType).toUpperCase());
                String type = elem.elementText(StringUtil.componentType);
                String expect = elem.elementText(StringUtil.expect);
                String message = elem.elementText(StringUtil.message);
                result = new SingleComponentResult(index,resultType,type,expect,message);
                return result;
            }else if(name.equalsIgnoreCase(StringUtil.pixelsResult)){
                float x = Float.parseFloat(elem.elementText(StringUtil.Xray));
                float y = Float.parseFloat(elem.elementText(StringUtil.Yray));
                String color = elem.elementText(StringUtil.color);
                result = new PixelsResult(color,x,y);
                return result;
            }


            if(name.equalsIgnoreCase(StringUtil.or)){
                result = new OrOperation();
                List<Element> elements = elem.elements();
                ((OrOperation)result).setResult1(getResult(elements.get(0)));
                ((OrOperation)result).setResult2(getResult(elements.get(1)));

            }else if(name.equalsIgnoreCase(StringUtil.not)){
                result = new NotOperation();
                List<Element> elements = elem.elements();
                ((NotOperation)result).setResult(getResult(elements.get(0)));
            }else if(name.equalsIgnoreCase(StringUtil.and)){
                result = new AndOperation();
                List<Element> elements = elem.elements();
                ((AndOperation)result).setResult1(getResult(elements.get(0)));
                ((AndOperation)result).setResult2(getResult(elements.get(1)));
            }


        return result;
    }

    //TODO 根据边标签获取边
    private Edge getEdge(Element element){
        //虚拟边问题
        if(element.elements().size()==0){
            //虚拟边；
            return new VirtualOperation();
        }
        String destination = element.elementText(StringUtil.destination);
        String resource = element.elementText(StringUtil.resource);

        int delay = Integer.parseInt(element.elementText("delay"));

        String id = element.elementText(StringUtil.id);
        Action action = Action.valueOf(element.elementText(StringUtil.action).toUpperCase());

        Area area = getArea(element.element(StringUtil.area));

        Edge edge = new Operation(action,destination,resource,area,id,delay);
        return edge;
    }

    //TODO 根据area标签获取area对象
    private Area getArea(Element element){
        String type = element.attributeValue(StringUtil.type);
        Area area = null;
        //如果是singlePoint
        if(type.equalsIgnoreCase(StringUtil.singlePoint)){

            area = getPoint(element);

        }else if(type.equalsIgnoreCase(StringUtil.doublePoint)){
            //是一个doublePoint
            Iterator<Element> it = element.elementIterator(StringUtil.area);
            SinglePoint start = getPoint(it.next());
            SinglePoint end = getPoint(it.next());
            area = new DoublePoint(start,end);
        }else if(type.equalsIgnoreCase(StringUtil.range)){

            Iterator<Element> it = element.elementIterator(StringUtil.area);
            SinglePoint start = getPoint(it.next());
            SinglePoint end = getPoint(it.next());
            area = new Range(start,end);
        }else if(type.equalsIgnoreCase(StringUtil.dragRange)){
            Iterator<Element> it = element.elementIterator(StringUtil.area);

            Iterator<Element> its = it.next().elementIterator(StringUtil.area);
            SinglePoint start = getPoint(its.next());
            SinglePoint end = getPoint(its.next());
            SinglePoint begin = getPoint(it.next());

            area = new DragRange(start,end,begin);
        }else if(type.equalsIgnoreCase(StringUtil.component)){
            area = getComponent(element);
        }else if(type.equalsIgnoreCase(StringUtil.multiComponent)){
            area = new MultiComponent();
            List<Element> elements = element.elements(StringUtil.area);
            for(Element elem : elements){
                ((MultiComponent)area).addComponent(getComponent(elem));
            }

        }else if(type.equalsIgnoreCase(StringUtil.textComponent)){
            String componentType = element.elementText(StringUtil.type);
            String id = element.elementText(StringUtil.id);
            String input = element.elementText(StringUtil.input);
            String message = element.elementText(StringUtil.message);
            area = new TextComponent(componentType,id,message,input);
        }

        return area;
    }


    //获取SinglePoint
    private SinglePoint getPoint(Element element){
        String x = element.elementText(StringUtil.Xray);
        String y = element.elementText(StringUtil.Yray);
        SinglePoint singlePoint = new SinglePoint(Float.parseFloat(x),Float.parseFloat(y));
        return singlePoint;
    }

    //获取Component
    private Component getComponent(Element element){
        String type = element.elementText(StringUtil.type);
        String id = element.elementText(StringUtil.id);
        String message = element.elementText(StringUtil.message);
        Component component = new Component(type,id,message);
        return component;
    }
}
