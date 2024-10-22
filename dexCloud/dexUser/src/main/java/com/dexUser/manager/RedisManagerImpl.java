package com.dexUser.manager;

import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:14:17
 * @message:
 */
@Component
public class RedisManagerImpl implements RedisManager {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void depositBonus(bonusPojo bonus) {
        redisTemplate.opsForValue().set("bonus"+bonus.getId(),bonus);
    }

    @Override
    public void depositWare(warePojo ware) {
        redisTemplate.opsForValue().set("ware"+ware.getId(),ware);
    }

    /*存入的商品规格为standards+商品的id*/
    @Override
    public void depositStandards(List<standardsPojo> standards,int bonus_id) {
        redisTemplate.opsForList().rightPushAll("standard"+bonus_id,standards);
    }

    @Override
    public void saveUserLoginLog(String UserName) {
        String setValue = "user"+ getTimeNow.getStaticTimeNowYMD();

        if (!redisTemplate.opsForHash().hasKey(setValue,UserName)){
            /*新建*/
            redisTemplate.opsForHash().put(setValue,UserName,getTimeNow.getStaticTimeNowYMD());
            redisTemplate.expire(setValue,7,TimeUnit.DAYS);
        }


    }
}
