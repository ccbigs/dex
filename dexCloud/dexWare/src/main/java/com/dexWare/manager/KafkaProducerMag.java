package com.dexWare.manager;

import domain.bonusOrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author:左泽林
 * @date:日期:2021-09-11-时间:14:56
 * @message:
 */
@Component
public class KafkaProducerMag {

    @Autowired
    private KafkaTemplate<String, bonusOrderPojo> kafkaTemplate;


    public void sendProducerMessage(bonusOrderPojo bonusOrderPojo) {
        kafkaTemplate.send("first", bonusOrderPojo);
    }
}
