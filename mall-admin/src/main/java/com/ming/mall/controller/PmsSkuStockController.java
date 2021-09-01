package com.ming.mall.controller;


import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.PmsSkuStock;
import com.ming.mall.service.IPmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku的库存 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "PmsSkuStockController",description = "sku商品库存管理")
@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {

    @Autowired
    private IPmsSkuStockService skuStockService;

    @ApiOperation(value = "根据商品id和sku编码模糊查询")
    @GetMapping("/{pid}")
    public CommonResult getSkuStockByKeyword(@PathVariable Long pid, @RequestParam(required = false) String keyword) {
        List<PmsSkuStock> skuStocks = skuStockService.getSkuStockByKeyword(pid, keyword);
        return CommonResult.success(skuStocks);
    }


    @ApiOperation(value = "批量更新sku库存")
    @PostMapping("/update/{pid}")
    public CommonResult updateSkuStock(@PathVariable Long pid, @RequestBody List<PmsSkuStock> skuStockList) {
        boolean flag = skuStockService.updateBatchById(skuStockList);
        if (flag) {
            return CommonResult.success(flag);
        }else {
            return CommonResult.failed();
        }
    }

}
