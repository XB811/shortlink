package com.nageoffer.shortlink.admin.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @FileName UserLoginRespDTO
 * @Description 用户登录返回实体
 */
@Data
@Builder
public class UserLoginRespDTO {
    /**
    * 用户 Token
    */
    private String token;
}
