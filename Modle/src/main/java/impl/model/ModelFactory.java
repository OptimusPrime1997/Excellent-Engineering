package impl.model;

import util.xmlwriter.PathStrategyEnum;
import service.ModelService;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ModelFactory {

    public static ModelService getModelInstance(PathStrategyEnum pe,String savePath){
        switch(pe){
            case ORACLE_FRIST:
                return new Re_Model(savePath);
            case ROOT_FRIST:
                return new Model(savePath);
        }
        return null;
    }
}
