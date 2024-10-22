package com.dexBusman.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import java.util.concurrent.TimeUnit;

/**
 * @author:左泽林
 * @date:日期:2021-09-07-时间:11:39
 * @message:
 */
@Service
public class BusmanRedisMsgImpl implements BusmanRedisMsg{
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveBusmanLoginLog(String busmanName) {
        String setValue="busman"+ getTimeNow.getStaticTimeNowYMD();

        if (!redisTemplate.opsForHash().hasKey(setValue,busmanName)){
            /*新建*/
            redisTemplate.opsForHash().put(setValue,busmanName,getTimeNow.getStaticTimeNowYMD());
            redisTemplate.expire(setValue,7,TimeUnit.DAYS);
        }
    }
}
