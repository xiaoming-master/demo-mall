package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.mall.mapper.UmsRoleResourceRelationMapper;
import com.ming.mall.model.UmsRoleResourceRelation;
import com.ming.mall.service.AdminCacheService;
import com.ming.mall.service.IUmsRoleResourceRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台角色资源关系表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements IUmsRoleResourceRelationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsRoleResourceRelationServiceImpl.class);

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private AdminCacheService adminCacheService;

    /**
     * 给角色分配资源
     *
     * @param resourceIds
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public boolean allocResource(List<Long> resourceIds, Long roleId) {
        try {//删除旧的资源
            roleResourceRelationMapper.delete(new QueryWrapper<UmsRoleResourceRelation>().eq("role_id", roleId));

            //若resourceIds为null时，表示删除当前角色id,就不需要插入
            if (CollUtil.isNotEmpty(resourceIds)) {
                //封装UmsRoleResourceRelation对象
                ArrayList<UmsRoleResourceRelation> list = new ArrayList<>();
                for (Long resourceId : resourceIds) {
                    UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
                    relation.setRoleId(roleId);
                    relation.setResourceId(resourceId);
                    list.add(relation);
                }
                //批量插入
                this.saveBatch(list);
            }
            //删除redis中为该角色的管理员记录
            adminCacheService.delResourceListByRole(roleId);
            return true;
        } catch (Exception e) {
            LOGGER.warn("UmsRoleResourceRelationServiceImpl,allocResource() catch exception:{}", e.getMessage());
            return false;
        }
    }
}
