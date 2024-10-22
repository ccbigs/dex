package com.dexBusman;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:15:56
 * @message:
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.dexBusman.dao")
public class dexBusmanMain {
    public static void main(String[] args) {
        SpringApplication.run(dexBusmanMain.class,args);
    }
}
