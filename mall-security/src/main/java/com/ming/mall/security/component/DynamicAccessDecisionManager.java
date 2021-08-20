package com.ming.mall.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 动态权限决策管理器，决定是否有访问权限
 *
 * @Author: ming
 * @create: 2021-08-20 15:57
 * @program: demo-mall
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) {
        //访问接口不需要权限
        if (configAttributes.isEmpty()) {
            return;
        }

        //获取访问者拥有的权限资源
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (ConfigAttribute configAttribute : configAttributes) {
            //权限名字
            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority authority : authorities) {
                //只要有全选资源匹配，就直接放行
                if (authority.getAuthority().trim().equals(attribute)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("没有权限访问，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
