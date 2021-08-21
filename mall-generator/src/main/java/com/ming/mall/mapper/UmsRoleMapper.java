package com.ming.mall.mapper;

import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 通过管理员id获取菜单
     *
     * @param adminId
     * @return
     */
    List<UmsMenu> getMenuByAdminId(@Param(value = "adminId") Long adminId);

    /**
     * 获取管理员的角色信息
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleByAdminId(@Param(value = "adminId") Long adminId);

}
