package com.lee.blog.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;
import java.util.Objects;

/**
 * 文件处理工具类
 * @author lee
 * @create 2021-09-21 20:41
 **/
@Log4j2
public class FileUtil {

    /**
     * 得到文件扩展名
     *
     * @param fileName 文件名称
     * @return {@link String} 文件后缀
     */
    public static String getExtName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 转换file
     *
     * @param multipartFile 多部分文件
     * @return {@link File} file
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = Objects.requireNonNull(originalFilename).split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param inputStream 输入流
     * @param size        文件大小，单位kb
     * @return 压缩后图片
     */
    public static InputStream compressImage(InputStream inputStream, long size) throws IOException {
        ByteArrayOutputStream outputStream = null;
        double accuracy = getAccuracy(size / 1024);
        while (size > 3 * 1024) {
            outputStream = new ByteArrayOutputStream();
            Thumbnails.of(inputStream)
                    .scale(accuracy)
                    .outputQuality(accuracy)
                    .toOutputStream(outputStream);
            size = outputStream.toByteArray().length;
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        }
        if (Objects.nonNull(outputStream)) {
            outputStream.close();
        }
        return inputStream;
    }


    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2048) {
            accuracy = 0.6;
        } else if (size < 3072) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }
}
