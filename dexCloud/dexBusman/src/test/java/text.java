import com.dexBusman.dao.WaresDao;
import com.dexBusman.dexBusmanMain;
import com.dexBusman.manager.BusmanRedisMsg;
import com.dexBusman.service.OrdersService;
import domain.warePojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import qo.order_add_warePojo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-07-时间:14:31
 * @message:
 */
@SpringBootTest(classes = dexBusmanMain.class)
public class text {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private BusmanRedisMsg busmanRedisMsg;

    @Resource
    private WaresDao waresDao;

    @Test
    public void a(){
        busmanRedisMsg.saveBusmanLoginLog("nihao");
    }

    @Test
    public void b(){
        order_add_warePojo order_add_warePojo = ordersService.busmanSearchOrderByES(1, 2);
        System.out.println(order_add_warePojo);
    }

    @Test
    public void c(){
        List<warePojo> warePojos = waresDao.busmanAlreadyGroundWare(4);
        System.out.println(warePojos.toString());
    }
}
