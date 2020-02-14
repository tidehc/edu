package com.lyb.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import com.lyb.vod.constants.ALiYunVodConstants;
import com.lyb.vod.service.VideoService;
import com.lyb.vod.util.ALiYunVodSDKUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author liuyoubin
 * @since 2020/1/28 - 17:17
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    //获取阿里云账号的keyId和keySecret
    private String accessKeyId = ALiYunVodConstants.ACCESS_KEY_ID;
    private String accessKeySecret = ALiYunVodConstants.ACCESS_KEY_SECRET;


    @Override
    public String uploadVideo(MultipartFile file) {

        //获取视频文件的原文件名
        String filename = file.getOriginalFilename();
        //获取视频文件的文件名作为标题(不含后缀名)
        String title = filename.substring(0, filename.lastIndexOf('.'));

        InputStream inputStream;

        try {
            //获取文件输入流
            inputStream = file.getInputStream();
        } catch (IOException e) {
           throw new CustomizeException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_FAIL);
        }

        //构造上传请求对象
        UploadStreamRequest request = new UploadStreamRequest(
                accessKeyId, accessKeySecret, title, filename, inputStream);


        UploadVideoImpl uploadVideo = new UploadVideoImpl();

        //上传成功后获取响应对象
        UploadStreamResponse response = uploadVideo.uploadStream(request);

        String videoId = response.getVideoId();

        if(!response.isSuccess()){//上传失败
            /*
               如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
               其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
             * */
            log.error("阿里云上传错误 code:"+response.getCode()+"  message:"+response.getMessage());

            if(StringUtils.isEmpty(videoId)){
                throw new CustomizeException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_FAIL);
            }
        }

        return videoId;
    }

    @Override
    public void removeVideoByVideoId(String videoId) {

        try {
            //获取客户端操作对象
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //构造请求对象
            DeleteVideoRequest videoRequest = new DeleteVideoRequest();
            videoRequest.setVideoIds(videoId);
            //发送删除请求
            client.getAcsResponse(videoRequest);

        } catch (ClientException e) {
           throw new CustomizeException(ResultCodeEnum.VIDEO_CLIENT_EXCEPTION);
        }
    }


    @Override
    public void batchRemoveVideoByVideoIds(List<String> videoIdList) {
        //将videoId集合按照进行分隔
        String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList, ',');

        try {
            //获取客户端操作对象
            DefaultAcsClient client = ALiYunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //构造请求对象
            DeleteVideoRequest videoRequest = new DeleteVideoRequest();
            videoRequest.setVideoIds(videoIds);
            //发送删除请求
            client.getAcsResponse(videoRequest);

        } catch (ClientException e) {
            throw new CustomizeException(ResultCodeEnum.VIDEO_CLIENT_EXCEPTION);
        }
    }
}
