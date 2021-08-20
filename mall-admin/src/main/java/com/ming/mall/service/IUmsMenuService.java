package com.ming.mall.service;

import com.ming.mall.dto.UmsMenuNode;
import com.ming.mall.model.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IUmsMenuService extends IService<UmsMenu> {

    /**
     * 创建menu
     * @param umsMenu
     * @return
     */
    int createMenu(UmsMenu umsMenu);

    /**
     * 通过父级id获取菜单
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsMenu> getMenuByPage(Long parentId, Integer pageNum, Integer pageSize);

    /**
     * 获取树形菜单
     * @return
     */
    List<UmsMenuNode> getMenuByTree();

    /**
     * 修改菜单信息
     * @param id
     * @param umsMenu
     * @return
     */
    int updateMenu(Long id, UmsMenu umsMenu);
}
