package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.UmsMenuNode;
import com.ming.mall.mapper.UmsMenuMapper;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.service.IUmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {

    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public int createMenu(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        //设置菜单级别
        setLevel(umsMenu);
        return menuMapper.insert(umsMenu);
    }

    /**
     * 通过父级id获取菜单
     *
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<UmsMenu> getMenuByPage(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return menuMapper.selectList(new QueryWrapper<UmsMenu>()
                .eq("parent_id", parentId)
                .orderByDesc("id"));
    }

    /**
     * 获取树形菜单
     *
     * @return
     */
    @Override
    public List<UmsMenuNode> getMenuByTree() {
        List<UmsMenuNode> umsMenuNodes = menuMapper.getMenuByTree(0L);
        return umsMenuNodes;
    }

    @Override
    public int updateMenu(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        //修改等级
        setLevel(umsMenu);
        //更新
        return menuMapper.updateById(umsMenu);
    }


    /**
     * 设置menu等级
     *
     * @param umsMenu
     */
    private void setLevel(UmsMenu umsMenu) {
        Long parentId = umsMenu.getParentId();
        //没有父级菜单
        if (parentId == 0) {
            umsMenu.setLevel(0);
        } else {
            UmsMenu parentMenu = menuMapper.selectById(umsMenu.getParentId());
            if (parentMenu != null) {//有父级菜单，level+1
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }
    }
}
