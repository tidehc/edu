package com.lyb.vod.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liuyoubin
 * @since 2019/11/8 - 23:19
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 配置后台管理系统课程中心微服务接口文档
     */
    @Bean
    public Docket adminApiConfig(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("后台管理系统-阿里云视频点播微服务API文档")
                .description("本文档描述了后台管理阿里云视频点播微服务接口定义")
                .version("1.0")
                .contact(new Contact("liuyoubin", "http://lyb.com", "734635746@qq.com"))
                .build();

    }

}
