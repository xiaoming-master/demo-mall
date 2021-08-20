package com.ming.mall.mapper;

import com.ming.mall.model.UmsAdminRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface UmsAdminRoleRelationMapper extends BaseMapper<UmsAdminRoleRelation> {

    /**
     * 通过管理员id获取可访问的资源
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceListById(@Param(value = "adminId") long adminId);
}
