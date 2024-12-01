package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import org.springframework.stereotype.Service;

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
}
