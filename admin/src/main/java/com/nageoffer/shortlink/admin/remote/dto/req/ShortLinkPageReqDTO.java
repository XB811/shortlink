package com.nageoffer.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.dao.entity.ShortLinkDO;
import lombok.Data;

/**
 * @FileName ShortLinkPageReqDTO
 * @Description 短链接分页请求参数
 */
@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {
    /*
    短链接分组标识
     */
    private String gid;
}
