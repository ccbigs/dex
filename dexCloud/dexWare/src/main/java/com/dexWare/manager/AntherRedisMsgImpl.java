package com.dexWare.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:10:49
 * @message:
 */
@Service
public class AntherRedisMsgImpl implements AntherRedisMsg{

    @Autowired
    private RedisTemplate redisTemplate;

    /*网红带货数据*/
    @Override
    public void saveCargoRecord(int id, BigDecimal totalPrice) {
        if (redisTemplate.opsForHash().hasKey("cargoRecord",String.valueOf(id))){
            redisTemplate.opsForHash().increment("cargoRecord",String.valueOf(id),totalPrice.doubleValue());
        }else {
            redisTemplate.opsForHash().put("cargoRecord",String.valueOf(id),totalPrice.doubleValue());
        }
    }

    /*保存游客活跃量*/
    @Override
    public void savePeopleActive() {
        String hashKey=getTimeNow.getStaticTimeNowYMD();

        if (redisTemplate.hasKey("touristActivity")){
            if (redisTemplate.opsForHash().hasKey("touristActivity",hashKey)){
                redisTemplate.opsForHash().increment("touristActivity",hashKey,1);
            }else {
                redisTemplate.opsForHash().put("touristActivity",hashKey,1L);
            }
        }else {
            redisTemplate.opsForHash().put("touristActivity",hashKey,1L);
        }
    }
}
