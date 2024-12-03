package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;

/**
 * @FileName GroupService
 * @Description 短链接分组接口
 */

public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     *
     * @param groupName 短链接分组名
     * @return
     * @Date 2024/12/3 16:20
     */
    void register(String groupName);
}
