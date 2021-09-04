package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.SmsCouponParam;
import com.ming.mall.model.SmsCoupon;
import com.ming.mall.service.ISmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsCouponController", description = "优惠券管理")
@RestController
@RequestMapping("/coupon")
public class SmsCouponController {

    @Autowired
    private ISmsCouponService couponService;


    @ApiOperation(value = "根据优惠券名称或类型分页模糊查询")
    @GetMapping("/list")
    public CommonResult getCoupon(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) Integer type,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsCoupon> couponList = couponService.getCoupon(name, type, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(couponList));
    }


    @ApiOperation(value = "通过优惠券id获取优惠券信息")
    @GetMapping("/{id}")
    public CommonResult getCouponById(@PathVariable Long id) {
        SmsCouponParam coupon = couponService.getCouponById(id);
        return CommonResult.success(coupon);
    }


    @ApiOperation(value = "添加优惠券")
    @PostMapping("/create")
    public CommonResult addCoupon(@RequestBody SmsCouponParam couponParam) {

        int count = couponService.addCoupon(couponParam);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "删除优惠券")
    @PostMapping("/delete/{id}")
    public CommonResult deleteCoupon(@PathVariable Long id) {
        boolean flag = couponService.removeById(id);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "修改优惠券信息")
    @PostMapping("/update/{id}")
    public CommonResult updateCoupon(@PathVariable Long id,@RequestBody SmsCouponParam couponParam) {
        int count = couponService.updateCoupon(id,couponParam);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }
}
