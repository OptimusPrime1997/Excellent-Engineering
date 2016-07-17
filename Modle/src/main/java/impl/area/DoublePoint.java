package impl.area;

import service.Area;

/**
 * Created by 王栋 on 2016/7/12 0012.
 */


public class DoublePoint implements Area {
    protected SinglePoint startPoint;
    protected SinglePoint endPoint;

    public DoublePoint(SinglePoint start,SinglePoint end){
        startPoint = start;
        endPoint = end;
    }


    public String toXML() {

        return startPoint.toXML() + endPoint.toXML();
    }

    public String printArea() {
        return null;
    }

    public SinglePoint getStartPoint() {
        return startPoint;
    }
    public void setStartPoint(SinglePoint startPoint) {
        this.startPoint = startPoint;
    }
    public SinglePoint getEndPoint() {
        return endPoint;
    }
    public void setEndPoint(SinglePoint endPoint) {
        this.endPoint = endPoint;
    }

}
