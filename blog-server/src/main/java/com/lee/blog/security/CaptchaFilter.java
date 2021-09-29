package com.lee.blog.security;

import com.lee.blog.exception.CaptchaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 验证码过滤器
 * @author lee
 * @create 2021-09-09 21:25
 **/
@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url = httpServletRequest.getRequestURI();

        if ("/login".equals(url) && httpServletRequest.getMethod().equals("POST")) {
            //log.info("获取到login链接，正在校验验证码 -- " + url);
            try{
                // 校验验证码
                validate(httpServletRequest);
            } catch (CaptchaException e) {
                // 交给认证失败处理器
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse,e);
                return;
            }
        }
        //校验通过放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 校验验证码逻辑
     *      验证保存在session的验证码和表单提交的验证码是否一致
     * @param httpServletRequest
     */
    private void validate(HttpServletRequest httpServletRequest)  {

        String code = httpServletRequest.getParameter("code");
        String session_code = (String) httpServletRequest.getSession().getAttribute("session_code");
        //log.info("进入验证码校验===》"+code+"\n"+session_code);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(session_code)) {
            throw new CaptchaException("验证码错误");
        }

        if (!code.toLowerCase().equals(session_code.toLowerCase())) {
            throw new CaptchaException("验证码错误");
        }

    }
}
