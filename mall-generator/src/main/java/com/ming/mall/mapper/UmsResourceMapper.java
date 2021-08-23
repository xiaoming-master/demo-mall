package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 通过roleId获取资源
     *
     * @param roleId
     * @return
     */
    List<UmsResource> getResourceByRoleId(@Param(value = "roleId") Long roleId);

//    /**
//     * 获取有该资源的管理员信息
//     *
//     * @param resourceId
//     * @return
//     */
//    List<UmsAdmin> getAdminByResourceId(@Param(value = "resourceId") Long resourceId);
}
