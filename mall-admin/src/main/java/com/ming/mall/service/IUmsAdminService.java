package com.ming.mall.service;

import com.ming.mall.model.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.mall.model.UmsRole;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsAdminService extends IService<UmsAdmin> {
    /**
     * 通过账户名获取admin
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 登陆
     * @param password
     * @param username
     * @return
     */
    String login(String username,String password);

    /**
     * 通过adminId删除管理员
     * @param adminId
     * @return
     */
    boolean deleteAdminById(Long adminId);

    /**
     * 通过用户名或昵称查找用户
     * @param keyword 关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsAdmin> getAdminByName(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 注册
     * @param umsAdmin
     * @return
     */
    UmsAdmin register(UmsAdmin umsAdmin);

    /**
     * 根据管理员ID获取对应角色信息
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleInfoByAdminId(Long adminId);

    /**
     * 更新管理员角色信息
     * @param adminId
     * @param roleIds
     * @return
     */
    int updateRoles(Long adminId, Integer[] roleIds);

    /**
     * 更新管理员信息
     * @param id
     * @param admin
     * @return
     */
    int updateAdminInfo(Long id, UmsAdmin admin);

    /**
     * 通过邮箱获取管理员信息
     * @param email
     * @return
     */
    UmsAdmin getUserByEmail(String email);
}
