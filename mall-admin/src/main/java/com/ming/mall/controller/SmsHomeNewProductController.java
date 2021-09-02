package com.ming.mall.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.SmsHomeNewProduct;
import com.ming.mall.service.ISmsHomeNewProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsHomeNewProductController", value = "首页新品推荐")
@RestController
@RequestMapping("/home/newProduct")
public class SmsHomeNewProductController {

    @Autowired
    private ISmsHomeNewProductService homeNewProductService;


    @ApiOperation(value = "添加新品推荐")
    @ApiImplicitParam(name = "homeNewProduct", value = "新品对象")
    @PostMapping("/create")
    public CommonResult addRecommendNewProduct(@RequestBody List<SmsHomeNewProduct> homeNewProductList) {
        for (SmsHomeNewProduct newProduct : homeNewProductList) {
            newProduct.setSort(0);
            newProduct.setRecommendStatus(1);
        }
        boolean flag = homeNewProductService.saveBatch(homeNewProductList);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "根据推荐状态或商品名称分页模糊查询推荐新品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "模糊商品名称"),
            @ApiImplicitParam(name = "recommendStatus", value = "推荐状态")
    })
    @GetMapping("/list")
    public CommonResult getRecommendProduct(@RequestParam(required = false) String productName,
                                            @RequestParam(required = false) Integer recommendStatus,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeNewProduct> newProducts = homeNewProductService.getCommendProduct(productName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(newProducts));
    }

    @ApiOperation(value = "批量删除推荐的新品")
    @PostMapping("/delete")
    public CommonResult deleteRecommendProduct(@RequestParam List<Long> ids) {
        boolean flag = homeNewProductService.removeByIds(ids);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "批量更新新品推荐状态")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        boolean flag = false;
        if (CollUtil.isNotEmpty(ids)) {
            SmsHomeNewProduct newProduct = new SmsHomeNewProduct();
            newProduct.setRecommendStatus(recommendStatus);
            QueryWrapper<SmsHomeNewProduct> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", ids);
            flag = homeNewProductService.update(newProduct, queryWrapper);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "更新推荐新新品的排序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam(required = false) Integer sort) {
        boolean flag = false;
        if (sort != null) {
            SmsHomeNewProduct newProduct = new SmsHomeNewProduct();
            newProduct.setId(id);
            newProduct.setSort(sort);
            flag = homeNewProductService.updateById(newProduct);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


}
