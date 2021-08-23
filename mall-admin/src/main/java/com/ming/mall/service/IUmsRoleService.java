package com.ming.mall.service;

import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsRoleService extends IService<UmsRole> {

    /**
     * 通过管理员id获取菜单
     * @param id
     * @return
     */
    List<UmsMenu> getMenuByAdminId(Long id);

    /**
     * 获取管理员的角色信息
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleByAdminId(Long adminId);

    /**
     * 批量删除角色
     * @param ids 要删除的角色id集合
     * @return
     */
    boolean deleteRole(List<Long> ids);

    /**
     * 根据角色名称查询角色
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsRole> getRoleByKeyword(String keyword, Integer pageNum, Integer pageSize);
}
