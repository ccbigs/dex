package com.dexWare.manager;

import com.dexWare.dao.BonusWareDao;
import domain.bonusOrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author:左泽林
 * @date:日期:2021-09-11-时间:14:57
 * @message:
 */
@Component
public class KafakaConsumerMag {

    @Autowired
    private BonusWareDao bonusWareDao;

    /**
     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
     * @throws IOException
     */
    @KafkaListener(topics = {"first"})
    public void listen(bonusOrderPojo bonusorder) throws IOException {
        //System.out.println("process:"+record.topic()+record.partition()+record.toString());
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("kafkaConusmer"+bonusorder);

        bonusWareDao.TouristBuyBonusWare(bonusorder);

    }

}
