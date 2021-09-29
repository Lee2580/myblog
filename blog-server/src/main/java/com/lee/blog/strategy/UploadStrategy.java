package com.lee.blog.strategy;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.lee.common.enums.UploadModeEnum.getStrategy;

/**
 * 上传策略
 * @author lee
 * @create 2021-09-15 17:21
 **/
public interface UploadStrategy {

    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
    String uploadFile(MultipartFile file, String path);
}
