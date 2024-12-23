package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

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
    /**
    * 新增短链接分组，附带username
    * @param username 
     * @param groupName 
    * @return 
    * @Date 2024/12/5 03:41
    */
    void register(String username,String groupName);

    /**
    * 查询当前用户的短链接分组
    * @return List<ShortLinkGroupRespDTO>
    * @Date 2024/12/3 17:10
    */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
    * 修改分组信息
    * @param requestParam 
    * @return 
    * @Date 2024/12/3 18:33
    */
    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);
    /*
     删除短链接分组
    */
    void deleteGroup(String gid);

    /*
    排序短链接分组
    */
    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParams);
}
