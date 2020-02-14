package com.lyb.vod.sdk;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.lyb.vod.util.ALiYunVodSDKUtils;

import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/26 - 17:53
 */
public class ALiYunVodSDKAddress {

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("8fed2684bc3b4f8694d9be6578c1a68a");
        return client.getAcsResponse(request);
    }
    /*以下为调用示例*/
    public static void main(String[] argv) throws ClientException {
        DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient("LTAI4FmESLjiaK3biNzErbTt", "7XDybr1OK8FaDeyf1xfep00aVuoER0");
        GetPlayInfoResponse response = new GetPlayInfoResponse();


        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
