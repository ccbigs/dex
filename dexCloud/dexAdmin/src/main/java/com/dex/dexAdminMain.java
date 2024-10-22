package com.dex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:11:41
 * @message:
 */
@SpringBootApplication
@MapperScan("com.dex.dao")
@EnableDiscoveryClient
public class dexAdminMain {
    public static void main(String[] args) {
        SpringApplication.run(dexAdminMain.class,args);
    }
}
