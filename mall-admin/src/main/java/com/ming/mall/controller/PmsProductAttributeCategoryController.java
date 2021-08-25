package com.ming.mall.controller;


import cn.hutool.core.util.StrUtil;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.ProductAttributeCategoryWithAttr;
import com.ming.mall.model.PmsProductAttributeCategory;
import com.ming.mall.service.IPmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "PmsProductAttributeCategoryController", description = "商品属性分类管理")
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private IPmsProductAttributeCategoryService productAttributeCategoryService;

    @ApiOperation(value = "通过id获取商品属性分类信息")
    @GetMapping("/{id}")
    public CommonResult getAttributeCategoryById(@PathVariable Long id) {
        PmsProductAttributeCategory attributeCategory = productAttributeCategoryService.getById(id);
        return CommonResult.success(attributeCategory);
    }

    @ApiOperation(value = "添加商品属性分类")
    @PostMapping("/create")
    public CommonResult addProductAttributeCategory(@RequestParam String name) {
        if (StrUtil.isEmpty(name)) {
            return CommonResult.failed();
        }
        PmsProductAttributeCategory attributeCategory = new PmsProductAttributeCategory();
        attributeCategory.setName(name);
        attributeCategory.setAttributeCount(0);
        attributeCategory.setParamCount(0);
        boolean flag = productAttributeCategoryService.save(attributeCategory);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "通过id删除商品属性分类")
    @GetMapping("/delete/{id}")
    public CommonResult deleteAttributeCategory(@PathVariable Long id) {
        boolean flag = productAttributeCategoryService.removeById(id);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "分页获取商品属性分类信息")
    @GetMapping("/list")
    public CommonResult getAttributeCategory(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        List<PmsProductAttributeCategory> categories = productAttributeCategoryService.getAttributeCategory(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(categories));
    }

    @ApiOperation(value = "获取所有商品属性分类及其下属性")
    @GetMapping("/list/withAttr")
    public CommonResult getAttributeCategoryWithAttr() {
        List<ProductAttributeCategoryWithAttr> categoryWithAttrs = productAttributeCategoryService.getAttributeCategoryWithAttr();
        return CommonResult.success(categoryWithAttrs);
    }


    @ApiOperation(value = "修改商品属性分类信息")
    @PostMapping("/update/{id}")
    public CommonResult updateAttributeCategory(@PathVariable Long id, @RequestParam String name) {
        PmsProductAttributeCategory category = new PmsProductAttributeCategory();
        category.setId(id);
        category.setName(name);
        boolean flag = productAttributeCategoryService.updateById(category);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }


    }

}
