package com.nageoffer.shortlink.admin.remote.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @FileName ShortLinkCountQueryRespDTO
 * @Description 根据gid查询短链接数量
 */
@Data
@Builder
public class ShortLinkCountQueryRespDTO {
    /**
    * 短链接分组id
    */
    private String gid;
    /**
    * 该分组下短链接数量
    */
    private Integer shortLinkCount;
}
