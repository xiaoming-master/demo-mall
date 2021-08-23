package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsResource;
import com.ming.mall.model.UmsRole;
import com.ming.mall.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "UmsRoleController", description = "角色管理")
@RestController
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private IUmsRoleService roleService;

    @Autowired
    private IUmsRoleMenuRelationService roleMenuRelationService;

    @Autowired
    private IUmsRoleResourceRelationService roleResourceRelationService;

    @Autowired
    private IUmsMenuService menuService;

    @Autowired
    private IUmsResourceService resourceService;


    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult getAllRoles() {
        List<UmsRole> roles = roleService.list();
        return CommonResult.success(roles);
    }

    @ApiOperation(value = "给角色分配菜单")
    @PostMapping("/allocMenu")
    public CommonResult allocMenu(@RequestParam List<Long> menuIds,
                                  @RequestParam Long roleId) {
        boolean flag = roleMenuRelationService.allocMenu(menuIds, roleId);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource")
    public CommonResult allocResource(@RequestParam List<Long> resourceIds, @RequestParam Long roleId) {
        boolean falg = roleResourceRelationService.allocResource(resourceIds, roleId);
        if (falg) {
            return CommonResult.success(falg, "success");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/create")
    public CommonResult createRole(@RequestBody UmsRole role) {
        role.setCreateTime(new Date());
        boolean flag = roleService.save(role);
        if (flag) {
            return CommonResult.success(flag, "success");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "批量删除角色")
    @PostMapping("/delete")
    public CommonResult deleteRole(@RequestParam List<Long> ids) {
        boolean flag = roleService.deleteRole(ids);
        if (flag) {
            return CommonResult.success(flag, "success");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "根据角色名称查询角色")
    @GetMapping("/list")
    public CommonResult getRoleByKeyword(@RequestParam(required = false) String keyword,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        List<UmsRole> roles = roleService.getRoleByKeyword(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(roles));
    }

    @ApiOperation(value = "获取角色的菜单")
    @GetMapping("/listMenu/{roleId}")
    public CommonResult getMenuByRoleId(@PathVariable Long roleId) {
        List<UmsMenu> menus = menuService.getMenuByRoleId(roleId);
        return CommonResult.success(menus);
    }

    @ApiOperation(value = "通过角色id获取资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult getResourceByRoleId(@PathVariable Long roleId) {
        List<UmsResource> resources = resourceService.getResourceByRoleId(roleId);
        return CommonResult.success(resources);
    }

    @ApiOperation(value = "修改角色信息")
    @PostMapping("/update/{id}")
    public CommonResult updateRole(@PathVariable Long id,
                                   @RequestBody UmsRole role) {
        role.setId(id);
        boolean flag = roleService.updateById(role);
        if (flag) {
            return CommonResult.success(flag, "success");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateRoleStatus(@PathVariable Long id, @RequestParam Integer status) {
        UmsRole role = new UmsRole();
        role.setId(id);
        role.setStatus(status);
        boolean flag = roleService.updateById(role);
        if (flag) {
            return CommonResult.success(flag, "success");
        } else {
            return CommonResult.failed();
        }
    }

}
