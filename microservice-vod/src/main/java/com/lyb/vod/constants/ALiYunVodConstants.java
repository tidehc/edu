package com.lyb.vod.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author liuyoubin
 * @since 2020/1/28 - 14:49
 * 阿里云视频常量类
 */
@Component
public class ALiYunVodConstants {

    /**
     * AccessKeyId
     */
    @Value("${aliyun.vod.file.keyId}")
    public String keyId;

    /**
     * AccessKeySecret
     */
    @Value("${aliyun.vod.file.keySecret}")
    public String keySecret;


    public static String ACCESS_KEY_ID;

    public static String ACCESS_KEY_SECRET;


    @PostConstruct
    public void setStaticProperties(){
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }

}
