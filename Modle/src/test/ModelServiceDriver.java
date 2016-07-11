import org.junit.Test;
import service.ModelService;
import impl.Model;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2016/7/8.
 */
public class ModelServiceDriver {
    ModelService service = new Model();

    @Test
    public void testSetRoot(){
        assertEquals(true,true);
    }
}
