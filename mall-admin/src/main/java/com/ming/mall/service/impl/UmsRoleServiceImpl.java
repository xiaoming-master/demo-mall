package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.mapper.UmsRoleMapper;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsRole;
import com.ming.mall.service.AdminCacheService;
import com.ming.mall.service.IUmsRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsRoleServiceImpl.class);

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private AdminCacheService adminCacheService;

    /**
     * 通过管理员id获取菜单
     *
     * @param id
     * @return
     */
    @Override
    public List<UmsMenu> getMenuByAdminId(Long id) {
        List<UmsMenu> menus = roleMapper.getMenuByAdminId(id);
        return menus;
    }

    @Override
    public List<UmsRole> getRoleByAdminId(Long adminId) {
        List<UmsRole> roles = roleMapper.getRoleByAdminId(adminId);
        return roles;
    }

    /**
     * 批量删除角色
     *
     * @param ids 要删除的角色id集合
     * @return
     */
    @Override
    public boolean deleteRole(List<Long> ids) {
        try {
            boolean flag = this.removeByIds(ids);
            //删除redis中为此角色的管理员信息
            if (flag) {
                adminCacheService.delResourceListByRoleIds(ids);
            }
            return flag;
        } catch (Exception e) {
            LOGGER.warn("UmsRoleServiceImpl.deleteRole() catch Exception:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 根据角色名称查询角色
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<UmsRole> getRoleByKeyword(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(keyword), "name", keyword);
        return roleMapper.selectList(queryWrapper);
    }
}
