package com.ming.mall.service.impl;

import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsAdminRoleRelation;
import com.ming.mall.model.UmsResource;
import com.ming.mall.security.component.AdminUserDetail;
import com.ming.mall.service.AdminCacheService;
import com.ming.mall.service.IUmsAdminRoleRelationService;
import com.ming.mall.service.IUmsAdminService;
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
    private IUmsAdminService adminService;

    @Autowired
    private IUmsAdminRoleRelationService adminRoleRelationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsAdmin admin = adminService.getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resources = adminRoleRelationService.getResourceById(admin.getId());
            return new AdminUserDetail(admin, resources);
        }
        return null;
    }
}
