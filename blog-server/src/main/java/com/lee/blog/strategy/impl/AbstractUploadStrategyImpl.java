package com.lee.blog.strategy.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.lee.blog.util.FileUtil;
import com.lee.common.enums.FileExtEnum;
import com.lee.blog.strategy.UploadStrategy;
import com.lee.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


/**
 *  图片上传
 * @author lee
 * @create 2021-09-21 20:38
 **/
@Slf4j
@Service
public abstract class AbstractUploadStrategyImpl implements UploadStrategy {

    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 获取文件md5值
            String md5 = SecureUtil.md5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            //log.info("extName ====>"+extName);
            // 重新生成文件名
            String fileName = md5 + extName;
            // 判断文件是否已经上传
            if (!exists(path + fileName)) {
                InputStream inputStream;
                // 判断上传文件类型（图片，音频)
                switch (Objects.requireNonNull(FileExtEnum.getFileExt(extName))) {
                    case JPG:
                    case PNG:
                    case JPEG:
                        // 压缩图片 将getSize转化为kb
                        inputStream = FileUtil.compressImage(file.getInputStream(), file.getSize()/1024);
                        //inputStream = file.getInputStream();
                        break;
                    default:
                        inputStream = file.getInputStream();
                        break;
                }
                //上传
                upload(path, fileName, inputStream);
            }
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }

    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    public abstract Boolean exists(String filePath);

    /**
     * 获取文件访问url
     * @param s
     * @return
     */
    protected abstract String getFileAccessUrl(String s);

    /**
     *  上传
     * @param path
     * @param fileName
     * @param inputStream   输入流
     */
    protected abstract void upload(String path, String fileName, InputStream inputStream) throws IOException;
}
