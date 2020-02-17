package com.lyb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author liuyoubin
 * @since 2020/2/17 - 12:05
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApiGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayApplication.class, args);
    }
}
