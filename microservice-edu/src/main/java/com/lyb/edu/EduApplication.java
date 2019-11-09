package com.lyb.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liuyoubin
 * @since 2019/11/8 - 22:29
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lyb.edu","com.lyb.common"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
