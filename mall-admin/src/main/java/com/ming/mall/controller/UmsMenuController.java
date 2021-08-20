package com.ming.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.UmsMenuNode;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.service.IUmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "UmsMenuController",description = "菜单控制")
@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    @Autowired
    private IUmsMenuService menuService;

    @ApiOperation(value = "通过菜单id获取菜单详情")
    @GetMapping("/{id}")
    public CommonResult getMenuById(@PathVariable(required = true) Long id) {
        UmsMenu menu = menuService.getById(id);
        return CommonResult.success(menu);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/create")
    public CommonResult addMenu(@RequestBody(required = true) UmsMenu umsMenu) {
        int count = menuService.createMenu(umsMenu);
        if (count != 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "通过id删除菜单")
    @PostMapping("/{id}")
    public CommonResult deleteMenuById(@PathVariable Long id) {
        boolean flag = menuService.removeById(id);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "分页获取菜单")
    @GetMapping("/list/{parentId}")
    public CommonResult getMenuByPage(@PathVariable Long parentId//父级菜单id
            , @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "5") Integer pageSize) {

        List<UmsMenu> menus = menuService.getMenuByPage(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(menus));
    }

    @ApiOperation(value = "获取树形菜单")
    @GetMapping("/treeList")
    public CommonResult getMenuByTree() {
        List<UmsMenuNode> umsMenuNode = menuService.getMenuByTree();
        return CommonResult.success(umsMenuNode);
    }

    @ApiOperation(value = "修改菜单信息")
    @PostMapping("/update/{id}")
    public CommonResult updateMenu(@PathVariable Long id,@RequestBody UmsMenu umsMenu) {
        int count = menuService.updateMenu(id, umsMenu);
        if (count > 0) {
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改菜单栏显示隐藏状态")
    @PostMapping("/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable Long id, @RequestParam Integer hidden) {
        UmsMenu menu = new UmsMenu();
        menu.setId(id);
        menu.setHidden(hidden);
        boolean flag = menuService.updateById(menu);
        if (flag) {
            return CommonResult.success(flag);
        }else {
            return CommonResult.failed();
        }
    }
}
