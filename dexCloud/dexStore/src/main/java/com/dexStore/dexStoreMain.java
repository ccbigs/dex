package com.dexStore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:10:27
 * @message:
 */

@SpringBootApplication
@MapperScan("com.dexStore.dao")
@EnableDiscoveryClient
public class dexStoreMain {
    public static void main(String[] args) {
        SpringApplication.run(dexStoreMain.class,args);
    }
}
