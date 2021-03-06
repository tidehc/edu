package com.lyb.aliyunoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liuyoubin
 * @since 2019/11/19 - 23:51
 */
@EnableEurekaClient
@SpringBootApplication
public class AliyunOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliyunOssApplication.class,args);
    }
}
