package com.lyb.aliyunoss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.lyb.aliyunoss.service.FileService;
import com.lyb.aliyunoss.util.ConstantPropertiesUtil;
import com.lyb.common.constants.ResultCodeEnum;
import com.lyb.common.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author liuyoubin
 * @since 2019/11/20 - 0:35
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    /**
     * 上传文件到oss
     * @param file 文件对象
     * @return 文件的最终路径
     */
    @Override
    public String uploadFile(MultipartFile file) {

        //获取阿里云oss的相关配置信息
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;

        //文件上传到oss的最终路径
        String uploadUrl;

        try(InputStream inputStream = file.getInputStream()) {

            //实例化OSS实例
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);

            if(!ossClient.doesBucketExist(bucketName)){//bucket不存在
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            //文件名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf('.'));
            String newFileName = fileName+fileType;

            //文件上传目录
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //文件上传到服务器的具体位置
            String fileUrl =  fileHost+"/"+filePath+"/"+newFileName;

            //上传文件流
            ossClient.putObject(bucketName,fileUrl,inputStream);

            //关闭ossClient
            ossClient.shutdown();


            uploadUrl ="https://"+bucketName+"."+endPoint+"/"+fileUrl;

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CustomizeException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        return uploadUrl;
    }

}
