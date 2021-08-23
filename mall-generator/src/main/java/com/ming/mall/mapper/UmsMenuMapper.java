package com.ming.mall.mapper;

import com.ming.mall.dto.UmsMenuNode;
import com.ming.mall.model.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 获取树状菜单
     * @return
     */
    List<UmsMenuNode> getMenuByTree(@Param(value = "parentId") Long parentId);

    /**
     * 通过角色id获取菜单
     * @param roleId
     * @return
     */
    List<UmsMenu> getMenuByRoleId(@Param(value = "roleId") Long roleId);
}
