import com.dexUser.dexUserMain;
import com.dexUser.manager.RedisManager;
import com.dexUser.serviceImpl.BonusServiceImpl;
import domain.bonusPojo;
import domain.warePojo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author:左泽林
 * @date:日期:2021-08-14-时间:16:10
 * @message:
 */
@SpringBootTest(classes = dexUserMain.class)
public class text {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RedisManager redisManager;

    @Autowired
    private BonusServiceImpl bonusService;

    @Test
    void a(){
        String minuteAddYMDHMS = getTimeNow.getMinuteAddYMDHMS(120);
        System.out.println(minuteAddYMDHMS);
    }

    /*@Test
    void b(){
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi(); // 标记事务块的开始
                operations.opsForValue().set("1","names");
                return operations.exec(); // 执行以 {multi()} 开始的事务中的所有排队命令。
            }
        });

    }*/

    @Test
    void c(){
        String setValue = "user"+ getTimeNow.getStaticTimeNowYMD();
        /*
         * @author: 左先生
         * @date: 2021-09-07 15:25
         * @description:
         * redisTemplate.hasKey(setValue) 判断是否存在setValue值
         *redisTemplate.opsForHash().hasKey(setValue,i+"nihao") 判断在setValue是否存在i+"nihao"值
         */
        for (int i = 0; i <30 ; i++) {
            System.out.println(redisTemplate.hasKey(setValue));
            System.out.println(redisTemplate.opsForHash().hasKey(setValue,i+"nihao"));
            if (!redisTemplate.opsForHash().hasKey(setValue,i+"nihao")){
                /*新建*/
                redisTemplate.opsForHash().put(setValue,i+"nihao",getTimeNow.getStaticTimeNowYMD());
                /*设置过期时间*/
                System.out.println("是否输出"+redisTemplate.hasKey(i+"nihao"));
            }
            redisTemplate.expire(setValue,30,TimeUnit.SECONDS);
        }

        System.out.println("长度"+redisTemplate.opsForHash().size("1231"));
        System.out.println("长度"+redisTemplate.opsForHash().size(setValue));

    }


}
