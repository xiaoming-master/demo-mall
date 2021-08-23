package com.ming.mall.service;

import com.ming.mall.model.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsResourceService extends IService<UmsResource> {

    /**
     * 通过roleId获取资源
     * @param roleId
     * @return
     */
    List<UmsResource> getResourceByRoleId(Long roleId);

    /**
     *根据id删除资源
     * @param resourceId
     * @return
     */
    int deleteResourceById(Long resourceId);

    /**
     * 模糊分页查询资源
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsResource> getResourcesByKeyWord(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize);
}
