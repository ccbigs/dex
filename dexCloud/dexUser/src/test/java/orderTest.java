import com.dexUser.dao.OrdersDao;
import com.dexUser.dexUserMain;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import qo.order_add_warePojo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-10-05-时间:16:12
 * @message:
 */
@SpringBootTest(classes = dexUserMain.class)
public class orderTest {
    @Resource
    private OrdersDao orderTest;

    @Test
    public void getallorder(){
        List<order_add_warePojo> userTransitOrder = orderTest.getUserTransitOrder(1);
        System.out.println(userTransitOrder);
    }
}
