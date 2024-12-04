package com.nageoffer.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @FileName ShortLinkGroupRespDTO
 * @Description
 */
@Data
public class ShortLinkGroupRespDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组排序
     */
    private Integer sortOrder;
}
