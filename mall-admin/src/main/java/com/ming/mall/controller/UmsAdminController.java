package com.ming.mall.controller;


import cn.hutool.core.collection.CollUtil;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.UmsAdminLoginParam;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsRole;
import com.ming.mall.service.IUmsAdminService;
import com.ming.mall.service.IUmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "UmsAdminController", description = "后台用户管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHead}")
    private String tokenHead = "Bearer";

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private IUmsAdminService adminService;

    @Autowired
    private IUmsRoleService roleService;

    @ApiOperation(value = "管理员登陆")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap, "登陆成功");
    }

    @ApiOperation(value = "获取管理员信息，以及菜单")
    @GetMapping("/info")
    public CommonResult adminInfo(Principal principal) {

        HashMap<String, Object> map = new HashMap<>();

        //获取用户名
        String username = principal.getName();
        //获取管理员信息
        UmsAdmin admin = adminService.getAdminByUsername(username);

        //获取菜单
        List<UmsMenu> menuList = roleService.getMenuByAdminId(admin.getId());

        //获取去角色
        List<UmsRole> roleList = roleService.getRoleByAdminId(admin.getId());

        //封装角色的名称
        if (CollUtil.isNotEmpty(roleList)) {
            ArrayList<UmsRole> roles = new ArrayList<>();
            roleList.forEach(role->{
                UmsRole umsRole = new UmsRole();
                umsRole.setName(role.getName());
                roles.add(umsRole);
            });
            map.put("roles", roles);
        }
        map.put("username", admin.getUsername());
        map.put("menus", menuList);
        map.put("icon", admin.getIcon());
        return CommonResult.success(map);
    }

}
