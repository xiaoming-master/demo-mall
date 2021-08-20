package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.mall.mapper.UmsRoleMapper;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsRole;
import com.ming.mall.service.IUmsRoleService;
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

    @Autowired
    private UmsRoleMapper roleMapper;

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
        List<UmsRole> roles=roleMapper.getRoleByAdminId(adminId);
        return roles;
    }
}
