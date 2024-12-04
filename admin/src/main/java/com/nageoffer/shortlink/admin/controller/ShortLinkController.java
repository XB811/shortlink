package com.nageoffer.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName ShortLinkController
 * @Description 短链接控制层
 */
@RestController

public class ShortLinkController {
    
    /**
    * 使用http调用的方式分页查询
    * @param requestParam 
    * @return Result<IPage<ShortLinkPageRespDTO>> 
    * @Date 2024/12/4 21:04
    */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
        };
        return shortLinkRemoteService.pageShortLink(requestParam);
    }
    /**
    * 新增短链接
    * @param requestParam 
    * @return Result<ShortLinkCreateRespDTO> 
    * @Date 2024/12/5 02:56
    */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
        };
        return shortLinkRemoteService.createShortLink(requestParam);
    }
}
