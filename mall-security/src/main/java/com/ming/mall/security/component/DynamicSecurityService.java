package com.ming.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务
 */
public interface DynamicSecurityService {

    /**
     * 获取所有url以及其对应的资源
     * @return
     */
    Map<String, ConfigAttribute> loadDataSource();

}
