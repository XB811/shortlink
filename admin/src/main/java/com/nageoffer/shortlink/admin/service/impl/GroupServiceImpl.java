package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.img.gif.GifDecoder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dao.mapper.GroupMapper;
import com.nageoffer.shortlink.admin.service.GroupService;
import com.nageoffer.shortlink.admin.toolkit.RandomGenerator;
import org.springframework.stereotype.Service;

import static com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;

/**
 * @FileName GroupServiceImpl
 * @Description 短链接分组接口实现层
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

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
                //TODO 设置用户名
                .username(null)
                .build();
        baseMapper.insert(groupDO);
    }
    /*
    检测gid是否可用
     */
    private boolean hasGid(String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                //TODO 设置用户名
                .eq(GroupDO::getUsername, null);
        GroupDO hasGidFlag = baseMapper.selectOne(queryWrapper);
        return hasGidFlag == null;
    }
}
