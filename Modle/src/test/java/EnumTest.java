import impl.Action;
import org.junit.Test;

/**
 * Created by 王栋 on 2016/7/12 0012.
 */
public class EnumTest {

    @Test
    public void test1(){
        System.out.println(Action.DRAG.toString());
        System.out.println(Action.valueOf("drag".toUpperCase()));
    }
}
