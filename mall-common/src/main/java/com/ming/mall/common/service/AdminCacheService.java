package com.ming.mall.common.service;

import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsResource;

import java.util.List;

/**
 * admin缓冲策略
 */
public interface AdminCacheService {
    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long adminId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsAdmin admin);


    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdminByEmail(String email);

    /**
     * 设置缓存后台用户信息
     */
    void setAdminByEmail(UmsAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 设置缓存后台用户资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
