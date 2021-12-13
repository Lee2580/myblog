package com.lee.blog.strategy.impl;

import com.lee.common.exception.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * 本地上传策略
 * @author lee
 * @create 2021-09-21 20:38
 **/
@Service("localUploadStrategyImpl")
public class LocalUploadStrategyImpl extends AbstractUploadStrategyImpl {

    /**
     * 本地路径
     */
    @Value("${upload.local.path}")
    private String localPath;

    /**
     * 访问url
     */
    @Value("${upload.local.url}")
    private String localUrl;

    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    @Override
    public Boolean exists(String filePath) {
        return new File(localPath + filePath).exists();
    }

    /**
     * 获取文件访问url
     * @param s
     * @return
     */
    @Override
    protected String getFileAccessUrl(String s) {
        return localUrl + s;
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

        // 判断目录是否存在
        File directory = new File(localPath + path);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new BizException("创建目录失败");
            }
        }
        // 写入文件
        File file = new File(localPath + path + fileName);
        if (file.createNewFile()) {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
            bos.flush();
            inputStream.close();
            bis.close();
            bos.close();
        }
    }
}
