package com.ming.mall.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.PmsProductParam;
import com.ming.mall.dto.ProductQueryParam;
import com.ming.mall.model.PmsProduct;
import com.ming.mall.service.IPmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "PmsProductController", description = "商品管理")
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private IPmsProductService productService;

    @ApiOperation(value = "模糊分页查询商品")
    @GetMapping("/list")
    public CommonResult getProduct(ProductQueryParam queryParam,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        List<PmsProduct> pmsProducts = productService.getProduct(queryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsProducts));
    }

    @ApiOperation(value = "根据商品名或货号模糊查询")
    @GetMapping("/simpleList")
    public CommonResult simpleList(@RequestParam(required = false) String keyword) {
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        //表示查询未删除的商品
        queryWrapper.eq("delete_status", 0);
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.like("name", keyword);
            queryWrapper.like("product_sn", keyword);
        }
        List<PmsProduct> products = productService.list(queryWrapper);
        return CommonResult.success(products);
    }

    @ApiOperation(value = "根据id获取商品信息")
    @GetMapping("/updateInfo/{id}")
    public CommonResult getProductById(@PathVariable Long id) {
        PmsProduct product = productService.getProductById(id);
        return CommonResult.success(product);
    }

    @ApiOperation(value = "创建商品")
    @PostMapping("/create")
    public CommonResult addProduct(@RequestBody PmsProductParam productParam) {
        int count = productService.addProduct(productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation(value = "更新商品信息")
    @PostMapping("/update/{id}")
    public CommonResult updateProduct(@PathVariable Long id, @RequestBody PmsProductParam productParam) {
        int count = productService.updateProduct(id, productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "批量修改商品删除状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "需要修改删除状态的商品id"),
            @ApiImplicitParam(name = "deleteStatus", value = "需要修改的状态")
    })
    @PostMapping("/update/deleteStatus")
    public CommonResult updateDeleteStatus(@RequestParam Integer deleteStatus, @RequestParam List<Long> ids) {
        ArrayList<PmsProduct> products = new ArrayList<>();
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);

        PmsProduct product = new PmsProduct();
        product.setDeleteStatus(deleteStatus);
        boolean flag = productService.update(product, queryWrapper);
        if (flag) {
            return CommonResult.success(flag);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation(value = "批量设置新品状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "待更新状态的商品id"),
            @ApiImplicitParam(name = "newStatus", value = "新品状态")
    })
    @PostMapping("/update/newStatus")
    public CommonResult updateNewStatus(@RequestParam List<Long> ids, @RequestParam Integer newStatus) {

        PmsProduct product = new PmsProduct();
        product.setNewStatus(newStatus);
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        boolean flag = productService.update(product, queryWrapper);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "批量更新上架状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "商品id集合"),
            @ApiImplicitParam(name = "publishStatus", value = "上架状态")
    })
    @PostMapping("/update/publishStatus")
    public CommonResult updatePublishStatus(@RequestParam List<Long> ids, @RequestParam Integer publishStatus) {
        PmsProduct product = new PmsProduct();
        product.setPublishStatus(publishStatus);

        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        boolean flag = productService.update(product, queryWrapper);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "批量更新推荐状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "商品id集合"),
            @ApiImplicitParam(name = "recommendStatus", value = "推荐状态")
    })
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {

        PmsProduct product = new PmsProduct();
        product.setRecommandStatus(recommendStatus);
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        boolean falg = productService.update(product, queryWrapper);
        return falg ? CommonResult.success(falg) : CommonResult.failed();
    }

    @ApiOperation("更新审核状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "detail", value = "审核详情或者审核反馈"),
            @ApiImplicitParam(name = "ids", value = "商品id集合"),
            @ApiImplicitParam(name = "verifyStatus", value = "审核状态")
    })
    @PostMapping("/update/verifyStatus")
    public CommonResult updateVerifyStatus(String detail, List<Long> ids, Integer verifyStatus, Principal principal) {
        int count = productService.updateVerifyStatus(detail, ids, verifyStatus, principal.getName());
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

}
