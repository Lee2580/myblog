package com.lee.blog;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.blog.config.OssConfigProperties;
import com.lee.blog.entity.User;
import com.lee.blog.mapper.UserMapper;
import com.lee.blog.mapper.WebsiteConfigMapper;
import com.lee.blog.service.RedisService;
import com.lee.blog.vo.WebsiteConfigVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@SpringBootTest
class BlogServerApplicationTests {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WebsiteConfigMapper websiteConfigMapper;

    @Autowired
    OssConfigProperties ossConfigProperties;

    @Autowired
    RedisService redisService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        String encode = bCryptPasswordEncoder.encode("123456");
        boolean matches = bCryptPasswordEncoder.matches("123456", encode);
        System.out.println(encode + "\t" + matches);

    }

    @Test
    void test(){

        String username = "";

        List<User> resourceList = userMapper.selectList(new LambdaQueryWrapper<User>()
                .like(StringUtils.isNotBlank(username), User::getUsername, username));

        System.out.println(resourceList);
    }

    @Test
    void testInteger(){
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
    }

    @Test
    void stringTOJson(){
        String config = websiteConfigMapper.selectById(1L).getConfig();
        WebsiteConfigVo websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
        System.out.println(websiteConfigVo);
    }

    @Test
    void oSSTest(){

        OSS ossClient = new OSSClientBuilder().build(ossConfigProperties.getEndpoint(), ossConfigProperties.getAccessKeyId(), ossConfigProperties.getAccessKeySecret());
        System.out.println(ossClient);
        boolean exist;
        try {
             exist = ossClient.doesObjectExist(ossConfigProperties.getBucketName(), "timg1.jpg");
        }catch (OSSException e){
            exist = false;
        }finally {
            ossClient.shutdown();
        }

        System.out.println(exist);
    }

    @Test
    void testDate(){
        Date date = new Date();
        DateTime dateTime = DateUtil.endOfDay(date);
        System.out.println(date+"\t"+dateTime);

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().select().orderByDesc(User::getUserId).last("limit 1"));
        System.out.println(user);

    }

    @Test
    void testWebp() throws IOException {

        FileInputStream file =  new FileInputStream("E:\\图片\\commentBack.webp");
        byte[] bytes = new byte[30];

        file.read(bytes,0,bytes.length);

        int width = ((int) bytes[27] & 0xff)<<8 | ((int) bytes[26] & 0xff);
        int height = ((int) bytes[29] & 0xff)<<8 | ((int) bytes[28] & 0xff);
        System.out.println(width);
        System.out.println(height);
    }

    @Test
    void redisTest(){
        /*String hello = null;
        Boolean flag = redisTemplate.hasKey("hello");
        if (!flag){
            redisTemplate.opsForValue().set("hello", 0);
        }
        hello = redisService.get("hello").toString();
        Map<Object, Double> viewsCountMap = redisService.zAllScore("test");
        Double score = redisService.zScore("test", 1);
        Map<String, Object> map = redisService.hGetAll("test");
        Object test = redisService.get("test");
        Integer test2 = (Integer) test;
        System.out.println(hello);
        System.out.println(viewsCountMap);
        System.out.println(score);
        System.out.println(map);
        System.out.println(test+"\n"+test2);
        Object test1 = redisService.get("test");
        if (Objects.nonNull(test1)){
            hello = test1.toString();
        }else {
            redisTemplate.opsForValue().set("test", 0);
        }
        System.out.println(hello);*/
    }

}
