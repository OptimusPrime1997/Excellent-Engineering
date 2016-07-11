package impl.xmlwriter;

import impl.State;
import service.XMLWriter;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class XMLWriterImpl implements XMLWriter {
    private List<State> appStates;
    private String root;

    public void writeXML(List<State> appStates,String root){
        this.appStates = appStates;
        this.root = root;

        State rootState = null;
        for(State statePtr : appStates){
            if(statePtr.isThisName(root)){
                rootState = statePtr;
                break;
            }
        }
    }


}
