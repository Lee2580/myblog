package com.lee.blog.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.lee.blog.config.WeiboConfigProperties;
import com.lee.blog.dto.SocialTokenDTO;
import com.lee.blog.dto.SocialUserDTO;
import com.lee.blog.dto.WeiboTokenDTO;
import com.lee.blog.dto.WeiboUserDTO;
import com.lee.blog.vo.WeiboLoginVo;
import com.lee.common.enums.LoginTypeEnum;
import com.lee.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.lee.common.comstant.SocialLoginConst.*;
import static com.lee.common.enums.StatusCodeEnum.WEIBO_LOGIN_ERROR;


/**
 * 微博登录实现类
 * @author lee
 * @create 2021-09-18 14:30
 **/
@Service("weiboLoginStrategyImpl")
public class WeiboLoginStrategyImpl extends SocialLoginStrategyImpl {

    @Autowired
    WeiboConfigProperties weiboConfigProperties;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取微博用户信息
     * @param socialToken
     * @return
     */
    @Override
    protected SocialUserDTO getSocialUserInfo(SocialTokenDTO socialToken) {
        // 定义请求参数
        Map<String, String> data = new HashMap<>(2);
        data.put(UID, socialToken.getOpenId());
        data.put(ACCESS_TOKEN, socialToken.getAccessToken());
        // 获取微博用户信息
        WeiboUserDTO weiboUserDTO = restTemplate.getForObject(weiboConfigProperties.getUserInfoUrl(), WeiboUserDTO.class, data);
        // 返回用户信息
        return SocialUserDTO.builder()
                .nickname(Objects.requireNonNull(weiboUserDTO).getScreen_name())
                .avatar(weiboUserDTO.getAvatar_hd())
                .build();
    }

    /**
     * 从token获取用户信息
     * @param data
     * @return
     */
    @Override
    public SocialTokenDTO getSocialToken(String data) {

        WeiboLoginVo weiBoLoginVo = JSON.parseObject(data, WeiboLoginVo.class);
        // 获取微博token信息
        WeiboTokenDTO weiboToken = getWeiboToken(weiBoLoginVo);
        // 返回token信息
        return SocialTokenDTO.builder()
                .openId(weiboToken.getUid())
                .accessToken(weiboToken.getAccess_token())
                .loginType(LoginTypeEnum.WEIBO.getType())
                .build();
    }

    /**
     * 获取微博token信息
     * @param weiBoLoginVo
     * @return
     */
    private WeiboTokenDTO getWeiboToken(WeiboLoginVo weiBoLoginVo) {

        // 根据code换取微博uid和accessToken
        MultiValueMap<String, String> weiboData = new LinkedMultiValueMap<>();
        // 定义微博token请求参数
        weiboData.add(CLIENT_ID, weiboConfigProperties.getAppId());
        weiboData.add(CLIENT_SECRET, weiboConfigProperties.getAppSecret());
        weiboData.add(GRANT_TYPE, weiboConfigProperties.getGrantType());
        weiboData.add(REDIRECT_URI, weiboConfigProperties.getRedirectUrl());
        weiboData.add(CODE, weiBoLoginVo.getCode());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(weiboData, null);
        try {
            return restTemplate.exchange(weiboConfigProperties.getAccessTokenUrl(), HttpMethod.POST, requestEntity, WeiboTokenDTO.class).getBody();
        } catch (Exception e) {
            throw new BizException(WEIBO_LOGIN_ERROR);
        }
    }
}
