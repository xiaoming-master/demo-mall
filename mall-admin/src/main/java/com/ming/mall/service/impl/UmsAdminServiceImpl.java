package com.ming.mall.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.common.exception.Asserts;
import com.ming.mall.common.util.RequestUtil;
import com.ming.mall.mapper.UmsAdminLoginLogMapper;
import com.ming.mall.mapper.UmsAdminMapper;
import com.ming.mall.mapper.UmsAdminRoleRelationMapper;
import com.ming.mall.mapper.UmsRoleMapper;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsAdminLoginLog;
import com.ming.mall.model.UmsAdminRoleRelation;
import com.ming.mall.model.UmsRole;
import com.ming.mall.security.component.AdminUserDetail;
import com.ming.mall.security.component.IUserDetailsService;
import com.ming.mall.security.utils.JwtTokenUtil;
import com.ming.mall.common.service.AdminCacheService;
import com.ming.mall.service.IUmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //先在缓存中找
        UmsAdmin admin;
        admin = adminCacheService.getAdmin(username);
        //如果缓存中没有就在数据库中找
        if (admin == null) {
            List<UmsAdmin> admins = list(new QueryWrapper<UmsAdmin>().eq("username", username));
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
     * @param email
     * @return
     */
    @Override
    public UmsAdmin getUserByEmail(String email) {
        UmsAdmin admin ;
        admin = adminCacheService.getAdminByEmail(email);
        if (admin == null) {
            List<UmsAdmin> admins = list(new QueryWrapper<UmsAdmin>().eq("email", email));
            if (CollUtil.isNotEmpty(admins)) {
                admin = admins.get(0);
                adminCacheService.setAdminByEmail(admin);
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
     * 通过adminId删除管理员
     *
     * @param adminId
     * @return
     */
    @Override
    public boolean deleteAdminById(Long adminId) {
        adminCacheService.delAdmin(adminId);
        boolean flag = removeById(adminId);
        adminCacheService.delResourceList(adminId);

        return flag;
    }

    /**
     * 通过用户名或昵称查找用户
     *
     * @param keyword  关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<UmsAdmin> getAdminByName(String keyword, Integer pageNum, Integer pageSize) {
        //分页
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(keyword != null, "username", keyword)
                .or()
                .like(keyword != null, "nick_name", keyword);
        return adminMapper.selectList(queryWrapper);
    }

    /**
     * 注册
     *
     * @param umsAdmin
     * @return
     */
    @Override
    public UmsAdmin register(UmsAdmin umsAdmin) {
        //查看是否有相同的用户名
        Integer count = adminMapper.selectCount(new QueryWrapper<UmsAdmin>().eq("username", umsAdmin.getUsername()));
        if (count > 0) {
            return null;
        }
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //加密密码
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));

        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    /**
     * 根据管理员ID获取对应角色信息
     *
     * @param adminId
     * @return
     */
    @Override
    public List<UmsRole> getRoleInfoByAdminId(Long adminId) {
        List<UmsRole> roles = roleMapper.getRoleByAdminId(adminId);
        return roles;
    }

    /**
     * 批量更新管理员角色信息
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    @Transactional
    @Override
    public int updateRoles(Long adminId, Integer[] roleIds) {
        //删除旧的关系
        adminRoleRelationMapper.delete(new QueryWrapper<UmsAdminRoleRelation>().eq("admin_id", adminId));
        //建立新关系
        int count = adminRoleRelationMapper.insertRoleByIds(adminId, roleIds);
        //删除redis中的旧数据
        adminCacheService.delResourceList(adminId);
        return count;
    }

    /**
     * 更新管理员信息
     *
     * @param id
     * @param admin
     * @return
     */

    @Override
    public int updateAdminInfo(Long id, UmsAdmin admin) {
        //查询旧数据
        UmsAdmin oldAdmin = adminMapper.selectById(id);
        if (passwordEncoder.matches(oldAdmin.getPassword(), admin.getPassword())) {//密码相同，不需要修改
            admin.setPassword(null);
        } else {
            if (StrUtil.isEmpty(admin.getPassword())) {
                admin.setPassword(null);
            } else {
                //加密密码
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        admin.setId(id);
        int count = adminMapper.updateById(admin);
        //删除redis中的旧数据
        adminCacheService.delAdmin(id);
        return count;
    }



    /**
     * 添加登陆记录
     *
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
