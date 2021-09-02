package com.ming.mall.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.SmsHomeRecommendProduct;
import com.ming.mall.service.ISmsHomeRecommendProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsHomeRecommendProductController", value = "首页人气推荐")
@RestController
@RequestMapping("/home/recommendProduct")
public class SmsHomeRecommendProductController {

    @Autowired
    private ISmsHomeRecommendProductService homeRecommendProductService;

    @ApiOperation(value = "添加首页人气推荐商品")
    @PostMapping("/create")
    public CommonResult addRecommendProduct(@RequestBody List<SmsHomeRecommendProduct> homeBrandList) {
        boolean flag = false;
        if (CollUtil.isNotEmpty(homeBrandList)) {
            for (SmsHomeRecommendProduct recommendProduct : homeBrandList) {
                recommendProduct.setSort(0);
                recommendProduct.setRecommendStatus(1);
            }
            flag = homeRecommendProductService.saveBatch(homeBrandList);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed("请求参数不正确");
    }

    @ApiOperation(value = "根据商品名称或推荐状态模糊分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "模糊商品名称"),
            @ApiImplicitParam(name = "recommendStatus", value = "推荐状态")
    })
    @GetMapping("/list")
    public CommonResult getRecommendProduct(@RequestParam(required = false) String productName,
                                            @RequestParam(required = false) Integer recommendStatus,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeRecommendProduct> products = homeRecommendProductService.getRecommendProduct(productName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(products));
    }

    @ApiOperation(value = "批量删除")
    @PostMapping("/delete")
    public CommonResult deleteRecommendProduct(@RequestParam List<Long> ids) {
        boolean flag = homeRecommendProductService.removeByIds(ids);
        return flag ? CommonResult.success(flag) : CommonResult.failed("删除失败");
    }

    @ApiOperation(value = "批量修改推荐状态")
    @PostMapping("/update/recommendStatus")
    public CommonResult updaterRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
        product.setRecommendStatus(recommendStatus);

        QueryWrapper<SmsHomeRecommendProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(CollUtil.isNotEmpty(ids), "id", ids);

        boolean flag = homeRecommendProductService.update(product, queryWrapper);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "修改推荐排序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam(required = false) Integer sort) {
        boolean flag = false;
        if (sort != null) {
            SmsHomeRecommendProduct product = new SmsHomeRecommendProduct();
            product.setId(id);
            product.setSort(sort);
            flag = homeRecommendProductService.updateById(product);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed("失败");
    }
}
