import create_model.ModelCreate;
import service.ModelService;
import util.xmlwriter.PathStrategyEnum;

import java.io.File;

/**
 * Created by 王栋 on 2016/7/13 0013.
 */
public class TestModel {
    public static void main(String[] args){
        File[] file ;
        file = new File(TestModel.class.getClassLoader().getResource("test").getPath()).listFiles();
        ModelService modelService = new ModelCreate().getModel(PathStrategyEnum.ROOT_FRIST,file);
        modelService.printXML(10);
        modelService.printModel();
    }
}
