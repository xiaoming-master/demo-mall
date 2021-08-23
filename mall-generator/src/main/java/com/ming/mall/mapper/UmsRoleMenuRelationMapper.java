package com.ming.mall.mapper;

import com.ming.mall.model.UmsRoleMenuRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface UmsRoleMenuRelationMapper extends BaseMapper<UmsRoleMenuRelation> {

    /**
     * 给角色分配菜单
     *
     * @param menuIds
     * @param roleId
     * @return
     */
    int allocMenu(@Param(value = "menuIds") List<Long> menuIds, @Param(value = "roleId") Long roleId);
}
