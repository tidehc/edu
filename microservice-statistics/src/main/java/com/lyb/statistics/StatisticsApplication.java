package com.lyb.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuyoubin
 * @since 2020/1/16 - 22:05
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.lyb.statistics","com.lyb.common"})
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
