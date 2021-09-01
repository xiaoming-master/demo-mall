package com.ming.mall.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.UmsAdminLoginParam;
import com.ming.mall.model.UmsAdmin;
import com.ming.mall.model.UmsMenu;
import com.ming.mall.model.UmsRole;
import com.ming.mall.security.utils.JwtTokenUtil;
import com.ming.mall.service.IUmsAdminService;
import com.ming.mall.service.IUmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
            roleList.forEach(role -> {
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

    @ApiOperation(value = "通过id获取管理员信息")
    @GetMapping("/{id}")
    public CommonResult getAdminById(@PathVariable Long id) {
        UmsAdmin umsAdmin = adminService.getById(id);
        umsAdmin.setPassword("");
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "删除管理员")
    @PostMapping("/delete/{id}")
    public CommonResult deleteAdminById(@PathVariable Long id) {
        boolean flag = adminService.deleteAdminById(id);
        if (flag) {
            return CommonResult.success(flag);
        }else {
            return CommonResult.failed();
        }
    }


    @ApiOperation(value = "通过用户名或昵称查找用户")
    @GetMapping("/list")
    public CommonResult getAdminByName(@RequestParam(required = false) String keyword,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "5") Integer pageSize) {
        List<UmsAdmin> admins = adminService.getAdminByName(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(admins));
    }

    @ApiOperation(value = "登出功能")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success("登出成功");
    }

    @ApiOperation("/刷新token")
    @GetMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        String token = authToken.substring(tokenHead.length());
        String refreshToken = jwtTokenUtil.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已过期");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("tokenHead", tokenHeader);
        map.put("token", refreshToken);
        return CommonResult.success(map);
    }


    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody UmsAdmin umsAdmin) {
        UmsAdmin admin = adminService.register(umsAdmin);
        if (admin == null) {
            return CommonResult.failed("注册失败,用户名已被使用");
        }else {
            return CommonResult.success(admin);
        }
    }

    @ApiOperation(value = "根据管理员ID获取对应角色信息")
    @GetMapping("/role/{adminId}")
    public CommonResult getRoleInfoByAdminId(@PathVariable Long adminId) {
        List<UmsRole> roles = adminService.getRoleInfoByAdminId(adminId);
        return CommonResult.success(roles);
    }

    @ApiOperation(value = "更新管理员角色信息")
    @PostMapping("/role/update")
    public CommonResult updateRoles(@RequestParam Long adminId,@RequestParam Integer[] roleIds) {
        int count = adminService.updateRoles(adminId, roleIds);
        return CommonResult.success(count);
    }

    @ApiOperation("更新管理员信息")
    @PostMapping("/update/{id}")
    public CommonResult updateAdminInfo(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.updateAdminInfo(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改管理员状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        UmsAdmin admin = new UmsAdmin();
        admin.setId(id);
        admin.setStatus(status);
        boolean flag = adminService.updateById(admin);
        if (flag) {
            return CommonResult.success(flag);
        }else {
            return CommonResult.failed();
        }
    }
}
