package com.dexStore.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:10:03
 * @message:
 */
@Service
public class HomeRedisMsgImpl implements HomeRedisMsg{

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Map getOneData() {
        if (redisTemplate.hasKey("cargoRecord")){
            return  redisTemplate.opsForHash().entries("cargoRecord");
        }
        return null;
    }

    /*返回用户一段时间段的活跃度*/
    @Override
    public Long getUserDateActive(String UserDate) {
        if (redisTemplate.hasKey(UserDate)){
            return redisTemplate.opsForHash().size(UserDate);
        }else {
            return 0L;
        }

    }

    /*返回商家一段时间的活跃度*/
    @Override
    public Long getBusmanDateActive(String BusmanDate) {
        if (redisTemplate.hasKey(BusmanDate)){
            return redisTemplate.opsForHash().size(BusmanDate);
        }else {
            return 0L;
        }
    }

    /*返回游客一段时间的活跃度*/
    @Override
    public Long getTouristDateActive(String DateYMD) {
        if (redisTemplate.hasKey("touristActivity")){
            if (redisTemplate.opsForHash().hasKey("touristActivity",DateYMD)){
                Integer touristActivity = (Integer) redisTemplate.opsForHash().get("touristActivity", DateYMD);
                return Long.valueOf(touristActivity);
            }else {
                return 0L;
            }
        }
        return 0L;
    }
}
