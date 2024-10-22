package com.dexWare.manager;

import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:15:05
 * @message:
 */
@Component
public class BonusWareManagerImpl implements BonusWareManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public warePojo getWareByBonus(int ware_id) {
        String RedisWareID = "ware"+ware_id;
        warePojo ware = null;

        /*判断请求的键值是否存在*/
        if (redisTemplate.hasKey(RedisWareID)){
            ware = (warePojo) redisTemplate.opsForValue().get(RedisWareID);
        }

        return ware;

    }

    @Override
    public bonusPojo getBonus(int bonus_id) {
        String RedisBonusID = "bonus"+bonus_id;
        bonusPojo bonus = null;

        if (redisTemplate.hasKey(RedisBonusID)){
            bonus = (bonusPojo) redisTemplate.opsForValue().get("bonus"+bonus_id);
        }

        return bonus;
    }

    @Override
    public List<standardsPojo> getAllStandard(int bonus_id) {
        String RedisStandardID = "standard"+bonus_id;
        List<standardsPojo> standards = null;

        if (redisTemplate.hasKey(RedisStandardID)){
            standards = redisTemplate.opsForList().range("standard" + bonus_id, 0, -1);
        }

        return standards;
    }

    /*获取单个商品规格*/
    @Override
    public standardsPojo getStandardByID(int bonus_id, int standard_id) {
        String RedisStandardID = "standard"+bonus_id;
        List<standardsPojo> standards = null;
        standardsPojo standard = null;

        if (redisTemplate.hasKey(RedisStandardID)){
            standards = redisTemplate.opsForList().range("standard" + bonus_id, 0, -1);
        }

        for (standardsPojo standardPojo : standards) {
            if (standard_id == standardPojo.getId()){
                standard = standardPojo;
                break;
            }
        }

        return standard;
    }

    @Override
    public warePojo clearAndReturnWareMessage(int ware_id) {
        warePojo ware =(warePojo) redisTemplate.opsForValue().get("ware"+ware_id);
        redisTemplate.delete("ware"+ware_id);
        return ware;
    }

    /*5.购买商品，商品大于0，减少商品数量，商品小于0，关闭秒杀*/
    @Override
    public int commoditySpikeBusinessLogic(int ware_id,int buy_number,int standard_id,int bonus_id) {
        int flag=200;
        int index=0;
        /*获取值*/
        warePojo ware =(warePojo) redisTemplate.opsForValue().get("ware"+ware_id);
        List<standardsPojo> allStandard = this.getAllStandard(bonus_id);
        System.out.println(allStandard);
        /*if暂定*/
        if (allStandard == null){
            /*实现业务：当用户购买为1的时候，redis库存已经全部清空，但还是发送过来，我们需要重置它的页面*/
            System.out.println("枪杀已经完毕");
            flag =  202;
        }else {
            for (standardsPojo standard : allStandard) {
                /*确定购买的商品规格*/
                if (standard.getId() == standard_id) {
                    /*判断购买的商品数量是不是大于商品规格的库存数量*/
                    if (buy_number > standard.getStandards_number()) {
                        /*跳过，在前端应该有判断的(以防万一设置的)*/
                        flag = 500;
                    } else {
                        /*判断购买数量是否等于商品的最小数量（不是商品规格哦）*/
                        if (buy_number == ware.getWare_number()) {
                            /*商品卖完，关闭秒杀*/
                            flag = 201;
                            ware.setWare_number(0);
                            standard.setStandards_number(standard.getStandards_number() - buy_number);
                        } else {
                            /*正常流程，减少商品的数量，也减少商品规格的数量*/
                            ware.setWare_number(ware.getWare_number() - buy_number);
                            standard.setStandards_number(standard.getStandards_number() - buy_number);
                        }
                        /*开启事务*/
                        int finalIndex = index;
                        redisTemplate.execute(new SessionCallback<Object>() {
                            @Override
                            public Object execute(RedisOperations operations) throws DataAccessException {
                                operations.multi(); // 标记事务块的开始
                                /*商品事务*/
                                operations.opsForValue().set("ware" + ware.getId(), ware);
                                /*商品更新数据*/
                                operations.opsForList().set("standard" + bonus_id, finalIndex, standard);
                                return operations.exec(); // 执行以 {multi()} 开始的事务中的所有排队命令。
                            }
                        });
                    }
                    break;
                }
                index++;
            }
        }
/*        if (flag == 200 || flag == 201){
            redisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.multi(); // 标记事务块的开始

                    operations.opsForValue().set("ware"+ware.getId(),ware);

                    operations.opsForList().set("standard"+bonus_id,0,allStandard);
                    return operations.exec(); // 执行以 {multi()} 开始的事务中的所有排队命令。
                }
            });
        }*/


        return flag;
    }
    @Override
    public Boolean clearBonus(int bonus_id) {
        return redisTemplate.delete("bonus"+bonus_id);
    }

    @Override
    public Boolean clearStandardMessage(int bonus_id) {
        /*8.清除redis中的stanards信息*/
        return redisTemplate.delete("standard"+bonus_id);
    }

    /*移除商品信息和秒杀信息*//*
    @Override
    public void theSecondKillIsOver(int ware_id, int bonus_id) {
        Long remove =  redisTemplate.opsForSet().remove(String.valueOf(ware_id));
        Long remove1 = redisTemplate.opsForSet().remove(String.valueOf(bonus_id));
        System.out.println(remove);
        System.out.println(remove1);
    }*/
}
