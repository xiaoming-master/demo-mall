package com.ming.mall.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.mall.common.exception.Asserts;
import com.ming.mall.common.util.RequestUtil;
import com.ming.mall.mapper.UmsAdminLoginLogMapper;
import com.ming.mall.mapper.UmsAdminMapper;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsAdminLoginLog;
import com.ming.mall.security.component.AdminUserDetail;
import com.ming.mall.security.utils.JwtTokenUtil;
import com.ming.mall.service.AdminCacheService;
import com.ming.mall.service.IUmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private AdminCacheService adminCacheService;

    @Autowired
    private IUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsAdminLoginLogMapper adminLoginLogMapper;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //先在缓存中找
        UmsAdmin admin;
        admin = adminCacheService.getAdmin(username);
        //如果缓存中没有就在数据库中找
        if (admin == null) {
            List<UmsAdmin> admins = list(new QueryWrapper<UmsAdmin>().eq("username", username));
            if (!CollUtil.isEmpty(admins) || !(admins.size() > 0)) {
                admin = admins.get(0);
                //放入redis
                adminCacheService.setAdmin(admin);
            }
        }
        return admin;
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token = null;

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails == null) {
                Asserts.fail("用户名或密码错误");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("该账号被禁用");
            }
            //放入security上下文
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //添加登陆记录
            insertLoginLog(((AdminUserDetail) userDetails).getAdmin().getId());

            token = jwtTokenUtil.generateToken(userDetails);
        } catch (UsernameNotFoundException e) {
            LOGGER.warn("登陆异常：{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登陆记录
     * @param adminId
     */
    public void insertLoginLog(Long adminId) {
        if (adminId == null || adminId == 0) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(adminId);
        loginLog.setCreateTime(new Date());
        //获取request上下文
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取登陆用户真实ip
        String ip = RequestUtil.getRequestIp(attributes.getRequest());
        loginLog.setIp(ip);
        LOGGER.info("有用户登陆,ip:{}", ip);
        adminLoginLogMapper.insert(loginLog);
    }
}
