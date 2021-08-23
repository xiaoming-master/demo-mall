package com.ming.mall.controller;


import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.UmsResourceCategory;
import com.ming.mall.service.IUmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "UmsResourceCategoryController", description = "后台分类资源管理")
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private IUmsResourceCategoryService resourceCategoryService;


    @ApiOperation(value = "获取所有资源分类")
    @GetMapping("/listAll")
    public CommonResult getAllResourceCategory() {
        List<UmsResourceCategory> categories = resourceCategoryService.list();
        return CommonResult.success(categories);
    }

    @ApiOperation(value = "添加资源分类")
    @PostMapping("/create")
    public CommonResult addResourceCategory(@RequestBody UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        boolean flag = resourceCategoryService.save(umsResourceCategory);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "通过id删除分类")
    @PostMapping("/delete/{id}")
    public CommonResult deleteResourceCategory(@PathVariable Long id) {
        boolean flag = resourceCategoryService.removeById(id);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改分类数据")
    @PostMapping("/update/{id}")
    public CommonResult updateResourceCategory(@PathVariable Long id,
                                               @RequestBody UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        boolean falg = resourceCategoryService.updateById(umsResourceCategory);
        if (falg) {
            return CommonResult.success(falg);
        } else {
            return CommonResult.failed();
        }
    }
}
