import create_model.ModelCreate;
import service.ModelService;
import util.xmlwriter.PathStrategyEnum;

import java.io.File;

/**
 * Created by 王栋 on 2016/7/13 0013.
 */
public class TestModle{
    public static void main(String[] args){
        File[] file = new File[1];
        file[0] = new File(TestModle.class.getClassLoader().getResource("1.xml").getPath());
        ModelService modelService = new ModelCreate().getModel(PathStrategyEnum.ROOT_FRIST,file);
        modelService.printXML(10);
    }
}
