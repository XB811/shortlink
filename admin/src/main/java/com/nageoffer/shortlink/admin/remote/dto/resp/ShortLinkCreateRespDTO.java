package com.nageoffer.shortlink.admin.remote.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @FileName ShortLinkCreateRespDTO
 * @Description
 */
@Data
@Builder
public class ShortLinkCreateRespDTO {
    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

}
