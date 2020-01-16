package com.lyb.aliyunoss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author liuyoubin
 * @since 2019/11/20 - 0:31
 */
public interface FileService {

    /**
     * 上传文件到oss
     * @param file 文件对象
     * @Param host 上传路径
     * @return 文件的最终路径
     */
    String uploadFile(MultipartFile file,String host);

}
