package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.mall.mapper.UmsAdminRoleRelationMapper;
import com.ming.mall.model.UmsAdminRoleRelation;
import com.ming.mall.model.UmsResource;
import com.ming.mall.common.service.AdminCacheService;
import com.ming.mall.service.IUmsAdminRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements IUmsAdminRoleRelationService {

    @Autowired
    private AdminCacheService adminCacheService;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Override
    public List<UmsResource> getResourceById(long adminId) {
        //先从redis中获取
        List<UmsResource> resources;
        resources = adminCacheService.getResourceList(adminId);
        if (CollUtil.isEmpty(resources)) {
            resources = adminRoleRelationMapper.getResourceListById(adminId);
            adminCacheService.setResourceList(adminId, resources);
        }
        return resources;
    }
}
