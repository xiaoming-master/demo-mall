package com.ming.mall.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.*;
import com.ming.mall.model.OmsOrder;
import com.ming.mall.service.IOmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "OmsOrderController", description = "订单管理")
@RestController
@RequestMapping("/order")
public class OmsOrderController {

    @Autowired
    private IOmsOrderService orderService;


    @ApiOperation(value = "通过条件分页查询订单")
    @GetMapping("/list")
    public CommonResult getOrderByKeyword(OmsOrderParam orderParam,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "5") Integer pageSize) {
        List<OmsOrder> orders = orderService.getOrderByKeyword(orderParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(orders));
    }

    @ApiOperation(value = "根据订单id获取订单信息")
    @GetMapping("/{id}")
    public CommonResult getOrderById(@PathVariable Long id) {
        OmsOrderDetail orderDetail = orderService.getOrderById(id);
        return CommonResult.success(orderDetail);
    }


    @ApiOperation(value = "批量删除订单")
    @PostMapping("/delete")
    public CommonResult deleteOrder(@RequestParam List<Integer> ids) {
        boolean flag = false;
        if (CollUtil.isNotEmpty(ids)) {
            OmsOrder order = new OmsOrder();
            order.setDeleteStatus(1);
            flag = orderService.update(order, new QueryWrapper<OmsOrder>().in("id", ids));
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed("参数错误");
    }


    @ApiOperation(value = "批量关闭带订单")
    @PostMapping("/update/close")
    public CommonResult closeOrder(@RequestParam List<Long> ids, @RequestParam String note, Principal principal) {
        int count = orderService.closeOrder(ids, note, principal);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }


    @ApiOperation(value = "批量发货")
    @PostMapping("/update/delivery")
    public CommonResult deliveryProduct(@RequestBody List<OmsDeliveryParam> deliveryParamList, Principal principal) {
        int count = orderService.deliveryProduct(deliveryParamList, principal);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "修改商品金额")
    @PostMapping("/update/moneyInfo")
    public CommonResult updateMoneyInfo(@RequestBody OmsOrderMoneyInfoParam moneyInfoParam, Principal principal) {
        int count = orderService.updateMoneyInfo(moneyInfoParam, principal);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "添加订单备注")
    @PostMapping("/update/note")
    public CommonResult updateNote(@RequestParam Long id,
                                   @RequestParam String note,
                                   @RequestParam Integer status,
                                   Principal principal) {
        int count = orderService.updateNote(id, note, status, principal);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "修改收货人信息")
    @PostMapping("/update/receiverInfo")
    public CommonResult updateReceiverInfo(@RequestBody OmsOrderReceiverInfoParam receiverInfoParam, Principal principal) {
        int count = orderService.updateReceiverInfo(receiverInfoParam, principal);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

}
