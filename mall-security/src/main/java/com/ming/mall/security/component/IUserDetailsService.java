package com.ming.mall.security.component;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.service.AdminCacheService;
import com.ming.mall.mapper.UmsAdminMapper;
import com.ming.mall.mapper.UmsAdminRoleRelationMapper;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-18 15:51
 * @program: demo-mall
 */
@Service
public class IUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminCacheService adminCacheService;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private UmsAdminMapper adminMapper;

    /**
     * 用户名登陆
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resources = adminRoleRelationMapper.getResourceListById(admin.getId());
            return new AdminUserDetail(admin, resources);
        }
        return null;
    }

    /**
     * email登陆
     * @param emailCount
     * @return
     * @throws UsernameNotFoundException
     */
    public AdminUserDetail loadUserByEmail(String emailCount) throws UsernameNotFoundException {
        UmsAdmin admin = getUserByEmail(emailCount);
        if (admin == null) {
            throw new UsernameNotFoundException("账户或密码错误");
        }
        List<UmsResource> resources = adminRoleRelationMapper.getResourceListById(admin.getId());
        return new AdminUserDetail(admin, resources);
    }

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    public UmsAdmin getAdminByUsername(String username) {
        //先在缓存中找
        UmsAdmin admin;
        admin = adminCacheService.getAdmin(username);
        //如果缓存中没有就在数据库中找
        if (admin == null) {
            List<UmsAdmin> admins = adminMapper.selectList(new QueryWrapper<UmsAdmin>().eq("username", username));
            if (CollUtil.isNotEmpty(admins)) {
                admin = admins.get(0);
                //放入redis
                adminCacheService.setAdmin(admin);
            }
        }
        return admin;
    }

    /**
     * 通过邮箱获取管理员信息
     *
     * @param email
     * @return
     */
    public UmsAdmin getUserByEmail(String email) {
        UmsAdmin admin;
        admin = adminCacheService.getAdminByEmail(email);
        if (admin == null) {
            List<UmsAdmin> admins = adminMapper.selectList(new QueryWrapper<UmsAdmin>().eq("email", email));
            if (CollUtil.isNotEmpty(admins)) {
                admin = admins.get(0);
                adminCacheService.setAdminByEmail(admin);
            }
        }
        return admin;
    }
}


