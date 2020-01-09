package com.lyb.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author liuyoubin
 * @since 2019/11/14 - 16:45
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SysUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysUserApplication.class, args);
    }
}
