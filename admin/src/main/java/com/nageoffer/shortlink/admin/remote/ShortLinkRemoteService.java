package com.nageoffer.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkCountQueryRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName ShortLinkRemoteService
 * @Description 短链接中通远程调用
 */

public interface ShortLinkRemoteService  {
    /**
    * 远程调用短链接分页查询
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("gid",requestParam.getGid());
        requestMap.put("current",requestParam.getCurrent());
        requestMap.put("size",requestParam.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/admin/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>(){

        });
    }
    /**
    * 远程调用新增短链接
    * @param requestParam 
    * @return Result<ShortLinkCreateRespDTO> 
    * @Date 2024/12/4 21:22
    */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO requestParam){
        String resultStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/admin/v1/create",JSON.toJSONString(requestParam));
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }
    default Result<List<ShortLinkCountQueryRespDTO>> listShortLinkCount(List<String> requestParam){
        Map<String, Object> params = new HashMap<>();
        params.put("requestParam",requestParam);
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/admin/v1/count", params);
        return JSON.parseObject(resultStr, new TypeReference<>(){

        });
    }
}
