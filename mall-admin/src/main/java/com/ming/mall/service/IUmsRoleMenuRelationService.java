package com.ming.mall.service;

import com.ming.mall.model.UmsRoleMenuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsRoleMenuRelationService extends IService<UmsRoleMenuRelation> {

    /**
     * 给角色分配菜单
     * @param menuIds
     * @param roleId
     * @return
     */
    boolean allocMenu(List<Long> menuIds, Long roleId);
}
