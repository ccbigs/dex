package com.dex.config;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author:左泽林
 * @date:日期:2021-09-07-时间:10:28
 * @message:
 */
@Component
@Order(value = 1)
public class StartService implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===========项目启动就运行了==========");
    }
}
