package com.lyb.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuyoubin
 * @since 2019/11/8 - 22:29
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.lyb.edu","com.lyb.common"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
