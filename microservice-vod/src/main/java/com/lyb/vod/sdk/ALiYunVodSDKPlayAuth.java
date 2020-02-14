package com.lyb.vod.sdk;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lyb.vod.util.ALiYunVodSDKUtils;

/**
 * @author liuyoubin
 * @since 2020/1/26 - 17:14
 */
public class ALiYunVodSDKPlayAuth {

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("8fed2684bc3b4f8694d9be6578c1a68a");
        return client.getAcsResponse(request);
    }

    /*以下为调用示例*/
    public static void main(String[] argv) throws ClientException {

        DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient("LTAI4FmESLjiaK3biNzErbTt", "7XDybr1OK8FaDeyf1xfep00aVuoER0");

        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
