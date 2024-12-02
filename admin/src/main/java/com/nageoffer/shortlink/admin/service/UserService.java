package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;

/**
 * @FileName UserService
 * @Description 用户接口层
 */
public interface UserService  extends IService<UserDO> {
    /**
    * 根据用户名查询用户信息
    * @param username 用户名
    * @return UserRespDTO 用户返回实体
    * @Date 2024/12/2 02:21
    */
    UserRespDTO getUserByUsername(String username);

    /**
    * 查询用户名是否存在
    * @param username 用户名
    * @return Boolean 用户名存在返回 True，不存在返回 False
    * @Date 2024/12/2 04:25
    */
    Boolean hasUsername(String username);

    /**
     * 注册用户
     *
     * @param requestParam 注册用户请求参数
     */
    void register(UserRegisterReqDTO requestParam);

    /**
    * 根据用户名修改用户
    * @param requestParam 
    * @return 
    * @Date 2024/12/2 10:40
    */
    void update(UserUpdateReqDTO requestParam);
    /**
    * 用户登录
    * @param requestParam
    * @return UserLoginRespDTO
    * @Date 2024/12/2 10:59
    */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
    * 检查用户是否登录
    * @param token
     * @param username
    * @return Boolean
    * @Date 2024/12/2 11:20
    */
    Boolean checkLogin(String token, String username);

    /**
    * 退出登录
    * @param token
     * @param username
    * @return
    * @Date 2024/12/2 12:11
    */
    void logout(String token, String username);
}
