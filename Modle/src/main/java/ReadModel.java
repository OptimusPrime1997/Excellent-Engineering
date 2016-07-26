//import com.sun.javafx.sg.prism.NGShape;
import create_model.ModelCreateByModel;
import impl.model.Model;

import java.io.File;

/**
 * Created by 王栋 on 2016/7/20 0020.
 */
public class ReadModel {
    public static void main(String[] args){
        ModelCreateByModel modelCreate = new ModelCreateByModel();
        Model model = modelCreate.getModel(new File("paths/model.xml"));
//        model.printXML(10);
        model.printModel();

    }
}
