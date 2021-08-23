package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.service.RedisService;
import com.ming.mall.mapper.UmsAdminRoleRelationMapper;
import com.ming.mall.mapper.UmsRoleResourceRelationMapper;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsAdminRoleRelation;
import com.ming.mall.model.UmsResource;
import com.ming.mall.model.UmsRoleResourceRelation;
import com.ming.mall.service.AdminCacheService;
import com.ming.mall.service.IUmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-18 15:29
 * @program: demo-mall
 */
@Service
public class AdminCacheServiceImpl implements AdminCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IUmsAdminService adminService;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;
    /**
     * 数据库路名字
     */
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;


    /**
     * 过期时间
     */
    @Value("${redis.expire.common}")
    private String REDIS_EXPIRE_COMMON;

    /**
     * 通过id删除redis中的数据
     *
     * @param adminId
     */
    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin admin = adminService.getById(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {

    }

    @Override
    public void delResourceListByRole(Long roleId) {
        //获取roleId下的管理员
        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectList(new QueryWrapper<UmsAdminRoleRelation>().eq("role_id", roleId));
        if (CollUtil.isNotEmpty(relationList)) {
            //封装key
            ArrayList<String> keys = new ArrayList<>();
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            for (UmsAdminRoleRelation relation : relationList) {
                String key = keyPrefix + relation.getAdminId();
                keys.add(key);
            }
            //删除
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        //封装多条件
        QueryWrapper<UmsAdminRoleRelation> queryWrapper = new QueryWrapper<>();
        for (Long roleId : roleIds) {
            queryWrapper.or(!roleIds.isEmpty(), query -> query.eq("role_id", roleId));
        }

        List<UmsAdminRoleRelation> relations = adminRoleRelationMapper.selectList(queryWrapper);
        //封装key
        ArrayList<String> keys = new ArrayList<>();
        String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
        for (UmsAdminRoleRelation relation : relations) {
            String key = keyPrefix + relation.getAdminId();
            keys.add(key);
        }
        //删除
        redisService.del(keys);
    }

    @Override
    public void delResourceListByResource(Long resourceId) {
        //找到拥有该资源的角色id
        List<UmsRoleResourceRelation> relations = roleResourceRelationMapper.selectList(new QueryWrapper<UmsRoleResourceRelation>().eq("resource_id", resourceId));
        //提取roleId
        ArrayList<Long> roleIds = new ArrayList<>();
        for (UmsRoleResourceRelation relation : relations) {
            roleIds.add(relation.getRoleId());
        }
        this.delResourceListByRoleIds(roleIds);
    }

    /**
     * 获取缓存后台用户信息
     */
    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin);
    }


    /**
     * 缓存管理员可访问资源
     *
     * @param adminId
     * @param resourceList
     */
    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisService.get(key);
    }
}
