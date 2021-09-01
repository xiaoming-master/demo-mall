package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.PmsProductCategoryNode;
import com.ming.mall.dto.PmsProductCategoryParam;
import com.ming.mall.model.PmsProductCategory;
import com.ming.mall.service.IPmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */

@Api(tags = "PmsProductCategoryController", description = "商品分类管理")
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    private IPmsProductCategoryService pmsProductCategoryService;

    @ApiOperation(value = "通过id获取商品分类信息")
    @GetMapping("/{id}")
    public CommonResult getProductCategoryById(@PathVariable Long id) {
        PmsProductCategory productCategory = pmsProductCategoryService.getById(id);
        return CommonResult.success(productCategory);
    }


    @ApiOperation(value = "添加商品分类信息")
    @PostMapping("/create")
    public CommonResult addProductCategory(@RequestBody PmsProductCategoryParam pmsProductCategoryParam) {
        int count = pmsProductCategoryService.addProductCategory(pmsProductCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "删除商品分类")
    @PostMapping("/delete/{id}")
    public CommonResult deleteProductCateGoryById(@PathVariable Long id) {
        boolean flag = pmsProductCategoryService.removeById(id);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "通过父id获取分类信息")
    @GetMapping("/list/{parentId}")
    public CommonResult getProductCategoryByParentId(@PathVariable Long parentId,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsProductCategory> categories = pmsProductCategoryService.getProductCategoryByParentId(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(categories));
    }

    @ApiOperation(value = "获取商品分类及其子分类")
    @GetMapping("/list/withChildren")
    public CommonResult getProductCategoryWithChildren() {
        List<PmsProductCategoryNode> categoryNodes = pmsProductCategoryService.getProductCategoryWithChildren();
        return CommonResult.success(categoryNodes);
    }


    @ApiOperation(value = "更新商品分类信息")
    @PostMapping("/update/{id}")
    public CommonResult updateProductCategory(@PathVariable Long id, @RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = pmsProductCategoryService.updateProductCategory(id, productCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation(value = "更新导航栏显示状态")
    @PostMapping("/update/navStatus")
    public CommonResult updateNavStatus(@RequestParam List<Long> ids, @RequestParam Integer navStatus) {

        ArrayList<PmsProductCategory> productCategories = new ArrayList<>();
        for (Long id : ids) {
            PmsProductCategory category = new PmsProductCategory();
            category.setId(id);
            category.setNavStatus(navStatus);
            productCategories.add(category);
        }
        boolean flag = pmsProductCategoryService.updateBatchById(productCategories);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation(value = "更新显示状态")
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam List<Long> ids, @RequestParam Integer showStatus) {
        ArrayList<PmsProductCategory> productCategories = new ArrayList<>();
        for (Long id : ids) {
            PmsProductCategory category = new PmsProductCategory();
            category.setId(id);
            category.setShowStatus(showStatus);
            productCategories.add(category);
        }
        boolean flag = pmsProductCategoryService.updateBatchById(productCategories);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }
}
