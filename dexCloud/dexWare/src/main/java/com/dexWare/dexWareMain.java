package com.dexWare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:19:41
 * @message:
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.dexWare.dao")
public class dexWareMain {
    public static void main(String[] args) {
        SpringApplication.run(dexWareMain.class,args);
    }
}
