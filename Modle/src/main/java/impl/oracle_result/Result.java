package impl.oracle_result;

/**
 * Created by Administrator on 2016/7/11.
 */
public interface Result {
    /**
     * 该接口专用于保存Oracle中的结果定义
     * @return
     */
    public String toXML();

    /**
     * 用于打印Result
     * @return Result打印的String
     */
    public String printResult();
}
