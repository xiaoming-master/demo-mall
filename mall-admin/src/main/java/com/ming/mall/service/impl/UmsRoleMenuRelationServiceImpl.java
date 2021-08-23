package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.model.UmsRoleMenuRelation;
import com.ming.mall.mapper.UmsRoleMenuRelationMapper;
import com.ming.mall.service.IUmsRoleMenuRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台角色菜单关系表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements IUmsRoleMenuRelationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UmsRoleMenuRelationServiceImpl.class);

    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;

    /**
     * 给角色分配菜单
     *
     * @param menuIds
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public boolean allocMenu(List<Long> menuIds, Long roleId) {
        try {//删除旧的
            roleMenuRelationMapper.delete(new QueryWrapper<UmsRoleMenuRelation>().eq("role_id", roleId));
            //再添加新的
            ArrayList<UmsRoleMenuRelation> list = new ArrayList<>();
            menuIds.forEach(menuId -> {
                UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
                relation.setRoleId(roleId);
                relation.setMenuId(menuId);
                list.add(relation);
            });
            return this.saveBatch(list);
        } catch (Exception e) {
            LOGGER.warn("UmsRoleMenuRelationServiceImpl.allocMenu() catch exception:{}",e.getMessage());
            return false;
        }
//        return roleMenuRelationMapper.allocMenu(menuIds, roleId);
    }

}
