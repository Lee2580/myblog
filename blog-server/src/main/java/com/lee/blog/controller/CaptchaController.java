package com.lee.blog.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * 验证码模块
 * @author lee
 * @create 2021-09-26 08:52
 **/
@Slf4j
@Api(tags = "验证码模块")
@RestController
public class CaptchaController {

    @Autowired
    Producer producer;

    /**
     * 获取验证码图片
     * @return
     * @throws IOException
     */
    @GetMapping("/captcha")
    public void Captcha(HttpServletRequest request, HttpServletResponse resp) throws IOException {

        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
        HttpSession session = request.getSession(true);
        session.setAttribute("session_code", code);
        ImageIO.write(image, "jpg", resp.getOutputStream());

    }
}
