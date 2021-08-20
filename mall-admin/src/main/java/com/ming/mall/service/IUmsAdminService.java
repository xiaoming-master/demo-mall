package com.ming.mall.service;

import com.ming.mall.model.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsAdminService extends IService<UmsAdmin> {
    /**
     * 通过账户名获取admin
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 登陆
     * @param password
     * @param username
     * @return
     */
    String login(String username,String password);
}
