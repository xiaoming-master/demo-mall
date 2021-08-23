package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.UmsResource;
import com.ming.mall.security.component.DynamicSecurityMetadataSource;
import com.ming.mall.service.IUmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "UmsResourceController",description = "后台资源管理")
@RestController
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private IUmsResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation(value = "获取所有资源")
    @GetMapping("/listAll")
    public CommonResult getAllResource() {
        List<UmsResource> resources = resourceService.list();
        return CommonResult.success(resources);
    }

    @ApiOperation(value = "根据id获取资源")
    @GetMapping("/{id}")
    public CommonResult getResourceById(@PathVariable Long id) {
        UmsResource resource = resourceService.getById(id);
        return CommonResult.success(resource);
    }

    @ApiOperation(value = "添加资源")
    @PostMapping("/create")
    public CommonResult addResource(@RequestBody UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        boolean flag = resourceService.save(umsResource);
        if (flag) {
            return CommonResult.success("success");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "根据id删除资源")
    @PostMapping("/delete/{id}")
    public CommonResult deleteResourceById(@PathVariable Long id) {
        int count = resourceService.deleteResourceById(id);
        //清除security中的资源
        dynamicSecurityMetadataSource.clean();
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "分页模糊查询资源")
    @GetMapping("/list")
    public CommonResult getResourcesByKeyWords(@RequestParam(required = false) Long categoryId,
                                               @RequestParam(required = false) String nameKeyword,
                                               @RequestParam(required = false) String urlKeyword,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "5") Integer pageSize) {
        List<UmsResource> resources = resourceService.getResourcesByKeyWord(categoryId, nameKeyword, urlKeyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(resources));
    }


    @ApiOperation(value = "修改资源信息")
    @PostMapping("/update/{id}")
    public CommonResult updateResource(@PathVariable Long id, @RequestBody UmsResource umsResource) {
        umsResource.setId(id);
        boolean flag = resourceService.updateById(umsResource);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

}
