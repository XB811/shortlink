package com.nageoffer.shortlink.admin.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nageoffer.shortlink.admin.common.serialize.PhoneDesensitizationSerializer;
import lombok.Data;

/**
 * @FileName UserRespDTO
 * @Description 用户返回参数实体
 */
@Data
public class UserRespDTO {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    //在springboot进行json序列化时，
    // 使用PhoneDesensitizationSerializer对该字段进行json序列化，进行加密
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
