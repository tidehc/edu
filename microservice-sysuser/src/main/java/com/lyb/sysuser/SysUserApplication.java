package com.lyb.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author liuyoubin
 * @since 2019/11/14 - 16:45
 */
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SysUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysUserApplication.class, args);
    }
}
