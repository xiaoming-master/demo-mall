package com.ming.mall.config;

import com.ming.mall.model.UmsResource;
import com.ming.mall.security.component.DynamicSecurityService;
import com.ming.mall.security.config.SpringSecurityConfig;
import com.ming.mall.service.IUmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ming
 * @create: 2021-08-20 15:32
 * @program: demo-mall
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SpringSecurityConfig {

    @Autowired
    private IUmsResourceService resourceService;

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            /**
             * 获取所有url以及其对应的资源
             */
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                ConcurrentHashMap<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resources = resourceService.list();
                //映射关系
                for (UmsResource resource : resources) {
                    map.put(resource.getUrl(), new SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }

}
