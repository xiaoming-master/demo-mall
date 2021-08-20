package com.ming.mall.service;

import com.ming.mall.model.UmsAdminRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.mall.model.UmsResource;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsAdminRoleRelationService extends IService<UmsAdminRoleRelation> {

    /**
     * 通过管理员id获取可访问资源l
     * @param id
     * @return
     */
    List<UmsResource> getResourceById(long id);
}
