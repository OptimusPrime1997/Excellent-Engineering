package impl.area;

import service.Area;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 王栋 on 2016/7/15 0015.
 */
public class MultiComponent implements Area {
    private List<Component> componentList;

    public MultiComponent(){
        componentList = new ArrayList<Component>();
    }

    public void addComponent(Component component){
        componentList.add(component);
    }

    public Iterator<Component> getComponents(){
        return componentList.iterator();
    }
    public String toXML(){
        String result = "<MultiComponent>\n";
        for (Component component : componentList){
            result+=component.toXML();
        }
        result+="</MultiComponent>\n";
        return  result;
    }

    public String printArea() {
        String result = "<area type=\"MultiComponent\">\n";
        for(Component component : componentList){
            result+=component.printArea();
        }
        result+="</area>\n";
        return result;
    }

}
