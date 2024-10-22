import com.dexWare.dexWareMain;
import com.dexWare.service.BonusWareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:21:28
 * @message:
 */
@SpringBootTest(classes = dexWareMain.class)
public class text {

    @Autowired
    private BonusWareService bonusWareService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void a(){
        redisTemplate.opsForList().set("standard688646",3,122);


        /* redisTemplate.opsForList().*/
    }

}
