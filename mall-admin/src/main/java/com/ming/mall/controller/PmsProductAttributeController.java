package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.AttrInfo;
import com.ming.mall.dto.PmsProductAttributeParam;
import com.ming.mall.model.PmsProductAttribute;
import com.ming.mall.service.IPmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private IPmsProductAttributeService productAttributeService;

    @ApiOperation(value = "通过id获取商品属性")
    @GetMapping("/{id}")
    public CommonResult getProductAttributeById(@PathVariable Long id) {
        PmsProductAttribute productAttribute = productAttributeService.getById(id);
        return CommonResult.success(productAttribute);
    }


    @ApiOperation(value = "通过商品分类id获取属性以及分类信息")
    @GetMapping("/attrInfo/{productCategoryId}")
    public CommonResult getAttributeInfoByProductCateGoryId(@PathVariable Long productCategoryId) {
        List<AttrInfo> attrInfos = productAttributeService.getAttributeInfoByProductCateGoryId(productCategoryId);
        return CommonResult.success(attrInfos);
    }

    @ApiOperation(value = "添加商品属性")
    @PostMapping("/create")
    public CommonResult addProductAttribute(@RequestBody PmsProductAttributeParam productAttributeParam) {
        int count = productAttributeService.addProductAttribute(productAttributeParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "批量删除商品属性")
    @PostMapping("/delete")
    public CommonResult deleteProductAttributeByIds(@RequestParam List<Integer> ids) {
        int count = productAttributeService.deleteProductAttributeByIds(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("/根据分类属性查询分类信息")
    @GetMapping("/list/{cid}")
    public CommonResult getProductAttribute(@PathVariable Long cid,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam Integer type) {
        List<PmsProductAttribute> attributeList = productAttributeService.getProductAttribute(cid, pageNum, pageSize, type);
        return CommonResult.success(CommonPage.restPage(attributeList));
    }


    @ApiOperation(value = "修改商品属性信息")
    @PostMapping("/update/{id}")
    public CommonResult updateProductAttribute(@PathVariable Long id,
                                               @RequestBody PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute productAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, productAttribute);
        productAttribute.setId(id);
        boolean flag = productAttributeService.updateById(productAttribute);
        if (flag) {
            return CommonResult.success(flag);
        }else {
            return CommonResult.failed();
        }
    }
}