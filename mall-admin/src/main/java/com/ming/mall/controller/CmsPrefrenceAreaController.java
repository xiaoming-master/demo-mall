package com.ming.mall.controller;


import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.CmsPrefrenceArea;
import com.ming.mall.service.ICmsPrefrenceAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 优选专区 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "CmsPrefrenceAreaController", description = "商品优选管理")
@RestController
@RequestMapping("/prefrenceArea")
public class CmsPrefrenceAreaController {

    @Autowired
    private ICmsPrefrenceAreaService prefrenceAreaService;

    @ApiOperation(value = "获取所有商品优选")
    @GetMapping("/listAll")
    public CommonResult getAllPrefrenceArea() {
        List<CmsPrefrenceArea> prefrenceAreas = prefrenceAreaService.list();
        return CommonResult.success(prefrenceAreas);
    }
}
