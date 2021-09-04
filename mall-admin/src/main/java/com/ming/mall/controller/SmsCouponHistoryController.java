package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.SmsCouponHistory;
import com.ming.mall.service.ISmsCouponHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsCouponHistoryController", description = "优惠券领取使用记录管理")
@RestController
@RequestMapping("/couponHistory")
public class SmsCouponHistoryController {

    @Autowired
    private ISmsCouponHistoryService couponHistoryService;

    @ApiOperation(value = "通过优惠券id,使用状态，订单编号来查询历史记录信息")
    @GetMapping("/list")
    public CommonResult getCouponHistory(@RequestParam(required = false) Long couponId,
                                         @RequestParam(required = false) String orderSn,
                                         @RequestParam(required = false) Integer useStatus,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsCouponHistory> histories = couponHistoryService.getCouponHistory(couponId, orderSn, useStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(histories));
    }
}
