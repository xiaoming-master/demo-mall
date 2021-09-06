package com.ming.mall.controller;


import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.OmsOrderSetting;
import com.ming.mall.service.IOmsOrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单设置表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "OmsOrderSettingController", description = "订单设置管理")
@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {

    @Autowired
    private IOmsOrderSettingService orderSettingService;

    @ApiOperation(value = "获取指定订单设置")
    @GetMapping("/{id}")
    public CommonResult getSetting(@PathVariable Long id) {
        OmsOrderSetting setting = orderSettingService.getById(id);
        return CommonResult.success(setting);
    }

    @ApiOperation(value = "修改指定订单设置")
    @PostMapping("/update/{id}")
    public CommonResult updateSetting(@RequestBody OmsOrderSetting orderSetting, @PathVariable Long id) {
        orderSetting.setId(id);
        boolean flag = orderSettingService.updateById(orderSetting);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

}
