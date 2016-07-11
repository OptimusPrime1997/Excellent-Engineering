package impl;
import impl.xmlwriter.path_strategy.*;


/**
 * 逆邻接链表，用于进行从Oracle端的遍历
 * Created by Administrator on 2016/7/11.
 */
public class Re_Model extends Model {
    public Re_Model(){
        this.strategy = new OracleFrist();
    }
}
