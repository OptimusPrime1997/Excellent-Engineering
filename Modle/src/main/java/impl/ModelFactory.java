package impl;

import impl.xmlwriter.PathStrategyEnum;
import service.ModelService;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ModelFactory {

    public static ModelService getModelInstance(PathStrategyEnum pe){
        switch(pe){
            case ORACLE_FRIST:
                return new Re_Model();
            case ROOT_FRIST:
                return new Model();
        }
        return null;
    }
}
