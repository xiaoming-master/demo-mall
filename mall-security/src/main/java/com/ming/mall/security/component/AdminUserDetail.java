package com.ming.mall.security.component;

import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-18 14:01
 * @program: demo-mall
 */
public class AdminUserDetail implements UserDetails {
    private UmsAdmin admin;
    private List<UmsResource> resources;

    public AdminUserDetail(UmsAdmin admin, List<UmsResource> resources) {
        this.admin = admin;
        this.resources = resources;
    }

    /**
     * 获取admin的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UmsResource resource : resources) {
            grantedAuthorities.add(new SimpleGrantedAuthority(resource.getId() + ":" + resource.getName()));
        }
        return grantedAuthorities;
    }
    public UmsAdmin getAdmin() {
        return admin;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }

}
