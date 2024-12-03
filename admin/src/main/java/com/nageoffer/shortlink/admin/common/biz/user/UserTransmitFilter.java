package com.nageoffer.shortlink.admin.common.biz.user;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import groovy.util.logging.Slf4j;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import com.nageoffer.shortlink.admin.common.constant.UserConstant;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 用户信息传输过滤器
 *
 * @公众号：马丁玩编程，回复：加群，添加马哥微信（备注：12306）获取项目资料
 */
@lombok.extern.slf4j.Slf4j
@RequiredArgsConstructor
@Slf4j
public class UserTransmitFilter implements Filter {
    private final StringRedisTemplate redisTemplate;

    private static final List<String> IGNORE_URL = Lists.newArrayList(
            "/api/short-link/admin/v1/user/login",
            "/api/short-link/admin/v1/user/has-username"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURL = String.valueOf(httpServletRequest.getRequestURL());
        if(!IGNORE_URL.contains(requestURL)){
            String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
            String username = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
            //查询redis
            Object userInfoJsonStr = redisTemplate.opsForHash().get("login_" + username, token);
            if (userInfoJsonStr != null) {
                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                //log.info("当前用户："+userInfoDTO.toString());
                UserContext.setUser(userInfoDTO);
                //给redis中缓存的当前用户信息续期
                redisTemplate.expire("login_"+username,30, TimeUnit.MINUTES);
            }
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } finally {
                UserContext.removeUser();
            }
        }

    }
}
