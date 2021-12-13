package com.lee.blog.strategy.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.lee.blog.config.OssConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


/**
 * oss上传策略
 * @author lee
 * @create 2021-09-21 21:07
 **/
@Slf4j
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    OssConfigProperties ossConfigProperties;

    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    @Override
    public Boolean exists(String filePath) {
        //log.info(filePath);
        boolean exist;
        try {
           exist = getOssClient().doesObjectExist(ossConfigProperties.getBucketName(), filePath);
        }catch (OSSException e){
            exist = false;
        }

        return exist;
    }

    /**
     * 获取ossClient
     * @return
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(ossConfigProperties.getEndpoint(), ossConfigProperties.getAccessKeyId(), ossConfigProperties.getAccessKeySecret());
    }

    /**
     * 获取文件访问url
     * @param s
     * @return
     */
    @Override
    protected String getFileAccessUrl(String s) {
        return ossConfigProperties.getUrl() + s;
    }

    /**
     * 上传方法
     * @param path
     * @param fileName
     * @param inputStream   输入流
     * @throws IOException
     */
    @Override
    protected void upload(String path, String fileName, InputStream inputStream) throws IOException {
        getOssClient().putObject(ossConfigProperties.getBucketName(), path + fileName, inputStream);
    }


}
