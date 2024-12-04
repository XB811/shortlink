package com.nageoffer.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.project.common.convention.result.Result;
import com.nageoffer.shortlink.project.common.convention.result.Results;
import com.nageoffer.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkCountQueryRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName ShortLinkController
 * @Description
 */

@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    /**
    * 创建短链接
    * @param requestParam
    * @return Result<Void>
    * @Date 2024/12/4 15:19
    */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {

        return Results.success(shortLinkService.createShortLink(requestParam));
    }
    /**
    * 分页查询短链接
    * @param requestParam
    * @return Result<IPage<ShortLinkPageRespDTO>>
    * @Date 2024/12/4 18:56
    */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }

    /**
    * 查询短链接分组内数量
    * @return Result<List<ShortLinkCountQueryRespDTO>>
    * @Date 2024/12/4 22:02
    */
    @GetMapping("/api/short-link/admin/v1/count")
    public Result<List<ShortLinkCountQueryRespDTO>> listShortLinkCount(@RequestParam("requestParam") List<String> requestParam){
        return Results.success(shortLinkService.listShortLinkCount(requestParam));
    }
}
