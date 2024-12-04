package com.nageoffer.shortlink.admin.common.biz.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.nageoffer.shortlink.admin.common.constant.UserConstant;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import groovy.util.logging.Slf4j;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum.USER_TOKEN_FAIL;

/**
 * 用户信息传输过滤器
 *
 * @公众号：马丁玩编程，回复：加群，添加马哥微信（备注：12306）获取项目资料
 */
@RequiredArgsConstructor
@Slf4j
public class UserTransmitFilter implements Filter {
    private final StringRedisTemplate redisTemplate;

    //忽略的url
    private static final List<String> IGNORE_URI = Lists.newArrayList(
            "/api/short-link/admin/v1/user/login",
            "/api/short-link/admin/v1/user/has-username"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = String.valueOf(httpServletRequest.getRequestURI());
        //忽略指定的uri
        if(!IGNORE_URI.contains(requestURI)){
            String method = httpServletRequest.getMethod();
            //忽略掉"/api/short-link/admin/v1/user"且请求方法为POST的请求
            if(!(Objects.equals(requestURI,"/api/short-link/admin/v1/user")&&Objects.equals(method,"POST"))){
                String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
                String username = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
                //如果请求中缺少username或者token
                if(!StrUtil.isAllNotEmpty(username,token)){
                    throw new ClientException(USER_TOKEN_FAIL);
                }
                //查询redis
                Object userInfoJsonStr =null;
                try {
                    userInfoJsonStr = redisTemplate.opsForHash().get("login_" + username, token);
                    if(userInfoJsonStr ==null){
                        throw new ClientException(USER_TOKEN_FAIL);
                    }
                } catch (Exception e) {
                    throw new ClientException(USER_TOKEN_FAIL);
                }
                //写入ThreadLocal
                UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
                UserContext.setUser(userInfoDTO);
                //给redis中缓存的当前用户信息续期
                redisTemplate.expire("login_"+username,30, TimeUnit.MINUTES);
            }
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}
