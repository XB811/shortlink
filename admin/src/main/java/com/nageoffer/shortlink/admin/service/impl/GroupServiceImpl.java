package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.biz.user.UserContext;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dao.mapper.GroupMapper;
import com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkCountQueryRespDTO;
import com.nageoffer.shortlink.admin.service.GroupService;
import com.nageoffer.shortlink.admin.toolkit.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName GroupServiceImpl
 * @Description 短链接分组接口实现层
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    @Override
    public void register(String groupName) {
        //生成6位的随机分组id
        String gid;
        while(true) {
            gid = RandomGenerator.generateRandom();
            if(hasGid(gid))break;
        }
        //新增到数据库
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .sortOrder(0)
                // 设置用户名
                .username(UserContext.getUsername())
                .build();
        baseMapper.insert(groupDO);
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag,0)
                // 设置用户名
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder)
                .orderByDesc(GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        //远程调用获取gid对应的短链接数量
        List<ShortLinkCountQueryRespDTO> data = shortLinkRemoteService.listShortLinkCount(groupDOList.stream().map(GroupDO::getGid).toList()).getData();
        //List to Map
        Map<String,Integer> shortLinkCountMap =new HashMap<>();
        data.forEach(each->{
            shortLinkCountMap.put(each.getGid(),each.getShortLinkCount());
        });
        //整合响应结果
        List<ShortLinkGroupRespDTO> shortLinkGroupRespDTOList = BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
        shortLinkGroupRespDTOList.forEach(each->{
            each.setShortLinkCount(shortLinkCountMap.get(each.getGid()));
        });
        return shortLinkGroupRespDTOList;
    }

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getGid, requestParam.getGid())
                .eq(GroupDO::getUsername, UserContext.getUsername());
        baseMapper.update(BeanUtil.copyProperties(requestParam, GroupDO.class), updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag,0);
        GroupDO groupDO =GroupDO.builder().build();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, updateWrapper);
    }

    /*
    排序短链接分组
     */
    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> requestParams) {
        requestParams.forEach(requestParam -> {
            LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                    .set(GroupDO::getSortOrder, requestParam.getSortOrder())
                    .eq(GroupDO::getGid, requestParam.getGid())
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getDelFlag,0);
            update(updateWrapper);
        });
    }

    /*
    检测gid是否可用
     */
    private boolean hasGid(String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                // 设置用户名
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO hasGidFlag = baseMapper.selectOne(queryWrapper);
        return hasGidFlag == null;
    }
}
