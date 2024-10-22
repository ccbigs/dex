import com.dexStore.dao.CartDao;
import com.dexStore.dao.HomeDao;
import com.dexStore.dexStoreMain;
import com.dexStore.manager.HomeRedisMsg;
import com.dexStore.service.CartService;
import com.dexStore.service.HomeService;
import com.dexStore.serviceImpl.CartServiceImpl;
import dto.HTwoVolume;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import utils.getTimeNow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.math.BigDecimal;
import java.util.*;

/**
 * @author:左泽林
 * @date:日期:2021-08-24-时间:16:47
 * @message:
 */
@SpringBootTest(classes = dexStoreMain.class)
public class text {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HomeDao homeDao;

    @Autowired
    private HomeRedisMsg homeRedisMsg;

    @Autowired
    private HomeService homeService;

    @Test
    void a(){
       cartDao.userUpdateCartNumber(14,2);
        /* cartService.userUpdateCartNumber(14,2);*/
    }

    @Test
    void b(){
        /*HashMap<String, BigDecimal> hashMap = new HashMap<>();
        hashMap.put("admin",new BigDecimal(100));
        hashMap.put("user",new BigDecimal(110));
        hashMap.put("wife",new BigDecimal(1000));
        System.out.println(hashMap);*/
        HashMap values = (HashMap)redisTemplate.opsForHash().entries("cargoRecord");
        System.out.println(values);
    }


    @Test
    void c(){
        redisTemplate.opsForHash().put("cargoRecord","37",new BigDecimal(633).doubleValue());
        redisTemplate.opsForHash().put("cargoRecord","38",new BigDecimal(493).doubleValue());
    }

    @Test
    void d(){
        if (redisTemplate.hasKey("cargoRecord")){
            double cargoRecord = (double) redisTemplate.opsForHash().get("cargoRecord", "1");
            System.out.println("原值"+cargoRecord);
            redisTemplate.opsForHash().increment("cargoRecord","1",new BigDecimal(100).doubleValue());
            //HashMap cargoRecorsd =(HashMap) redisTemplate.opsForHash().get("cargoRecord", "1");
            double cargoRecorsd = (double) redisTemplate.opsForHash().get("cargoRecord", "1");
            System.out.println("结果"+cargoRecorsd);
        }
    }

    @Test
    void e(){
        Map cargoRecord = redisTemplate.opsForHash().entries("cargoRecord");
        System.out.println(cargoRecord);


        List<Map.Entry<String,Double>> list = new ArrayList<>(cargoRecord.entrySet()); //将map的entryset放入list集合
        //对list进行排序，并通过Comparator传入自定义的排序规则
        //对list进行排序，并通过Comparator传入自定义的排序规则
        Collections.sort(list,new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                //重写排序规则，小于0表示升序，大于0表示降序
                return (int) -(o1.getValue()-o2.getValue());
            }
        });

        //用迭代器对list中的键值对元素进行遍历
        Iterator<Map.Entry<String, Double>> iter = list.iterator();
        while(iter.hasNext()){
            Map.Entry<String, Double> item = iter.next();
            String key = item.getKey();
            Double value = item.getValue();
            System.out.println("键"+key+"值"+value);
        }

    }

    @Test
    void f(){
        /*System.out.println(homeRedisMsg.getTouristDateActive(getTimeNow.getStaticTimeNowYMD()));*/
        HTwoVolume twoData = homeService.getTwoData();
        System.out.println(twoData);
    }

}
