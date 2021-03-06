package com.ming.mall.service;

import com.ming.mall.model.UmsRoleResourceRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台角色资源关系表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsRoleResourceRelationService extends IService<UmsRoleResourceRelation> {

    /**
     * 给角色分分配资源
     * @param resourceIds
     * @param roleId
     * @return
     */
    boolean allocResource(List<Long> resourceIds, Long roleId);
}
