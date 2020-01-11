package com.lyb.aliyunoss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author liuyoubin
 * @since 2019/11/20 - 0:31
 */
public interface FileService {

    String uploadFile(MultipartFile file,String host);

}
