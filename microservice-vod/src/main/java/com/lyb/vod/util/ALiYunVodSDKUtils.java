package com.lyb.vod.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author liuyoubin
 * @since 2020/1/26 - 14:11
 * 阿里云视频点播工具类
 */
public class ALiYunVodSDKUtils {

    /**
     * 根据accessKeyId和accessKeySecret获取Vod客户端对象
     * @param accessKeyId accessKeyId
     * @param accessKeySecret accessKeySecret
     * @return Vod客户端对象
     * @throws ClientException 异常
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }


}
