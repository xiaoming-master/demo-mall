package com.ming.mall.controller;


import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.OmsCompanyAddress;
import com.ming.mall.service.IOmsCompanyAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 公司收发货地址表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "OmsCompanyAddressController", description = "发货地址和退货地址管理")
@RestController
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {

    @Autowired
    private IOmsCompanyAddressService companyAddressService;

    @ApiOperation(value = "获取所有发货地址")
    @GetMapping("/list")
    public CommonResult getCompanyAddress() {
        List<OmsCompanyAddress> addressList = companyAddressService.list();
        return CommonResult.success(addressList);
    }
}
