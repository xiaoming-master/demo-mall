package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.mapper.UmsResourceMapper;
import com.ming.mall.mapper.UmsRoleResourceRelationMapper;
import com.ming.mall.model.UmsResource;
import com.ming.mall.service.AdminCacheService;
import com.ming.mall.service.IUmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements IUmsResourceService {

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Autowired
    private AdminCacheService adminCacheService;

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;


    /**
     * 通过roleID获取资源
     *
     * @param roleId
     * @return
     */
    @Override
    public List<UmsResource> getResourceByRoleId(Long roleId) {
        return resourceMapper.getResourceByRoleId(roleId);
    }

    /**
     * 根据id删除资源
     *
     * @param resourceId
     * @return
     */
    @Override
    public int deleteResourceById(Long resourceId) {
        //删除redis中的管理员资源信息
        adminCacheService.delResourceListByResource(resourceId);
        //删除数据库的资源
        return resourceMapper.deleteById(resourceId);
    }

    /**
     * 模糊分页查询资源
     *
     * @param categoryId  分类id
     * @param nameKeyword 资源名称关键字
     * @param urlKeyword  url
     * @param pageNum     页数
     * @param pageSize    页面大小
     * @return
     */
    @Override
    public List<UmsResource> getResourcesByKeyWord(Long categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        //拼接条件
        QueryWrapper<UmsResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(categoryId != null, "category_id", categoryId);
        queryWrapper.like(StrUtil.isNotEmpty(nameKeyword), "name", nameKeyword);
        queryWrapper.like(StrUtil.isNotEmpty(urlKeyword), "url", urlKeyword);
        //执行并返回
        return resourceMapper.selectList(queryWrapper);
    }
}
