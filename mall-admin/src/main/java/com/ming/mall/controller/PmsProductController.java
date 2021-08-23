package com.ming.mall.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.ProductQueryParam;
import com.ming.mall.model.PmsProduct;
import com.ming.mall.service.IPmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private IPmsProductService pmsProductService;

    @ApiOperation(value = "模糊分页查询商品")
    @GetMapping("/list")
    public CommonResult getProduct(@RequestParam ProductQueryParam queryParam,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsProduct> pmsProducts = pmsProductService.getProduct(queryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsProducts));
    }

    @ApiOperation(value = "根据商品名或货号模糊查询")
    @GetMapping("/simpleList")
    public CommonResult simpleList(@RequestParam String keyword) {
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        //表示查询未删除的商品
        queryWrapper.eq("delete_status", 0);
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.like("name", keyword);
            queryWrapper.like("product_sn", keyword);
        }
        List<PmsProduct> products = pmsProductService.list(queryWrapper);
        return CommonResult.success(products);
    }


}
