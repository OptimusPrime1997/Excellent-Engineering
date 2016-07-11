package service;
import impl.State;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */

public interface XMLWriter {
    /**
     * 该接口用于将模型转换成测试层能够识别的XML文件
     * @param appStates 导入的app状态模型
     */
    public void writeXML(List<State> appStates,String root);
}
