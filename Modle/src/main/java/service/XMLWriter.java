package service;
import impl.State;
import impl.xmlwriter.PathStrategy;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */

public interface XMLWriter {
    /**
     * 该接口用于将模型转换成测试层能够识别的XML文件
     * @param strategy 寻路的策略
     */
    public void writeXML(PathStrategy strategy);
}
