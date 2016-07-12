package impl.model;
import util.xmlWriter.path_strategy.*;


/**
 * 逆邻接链表，用于进行从Oracle端的遍历
 * Created by Administrator on 2016/7/11.
 */
public class Re_Model extends Model {

    /**
     * 由于使用逆邻接链表，训练策略也要做相应的改动
     */
    public Re_Model(){
        this.strategy = new OracleFrist();
    }



}
