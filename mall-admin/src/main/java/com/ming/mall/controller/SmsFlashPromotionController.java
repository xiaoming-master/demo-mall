package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.SmsFlashPromotion;
import com.ming.mall.service.ISmsFlashPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 限时购表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsFlashPromotionController", description = "限时活动管理")
@RestController
@RequestMapping("/flash")
public class SmsFlashPromotionController {

    @Autowired
    private ISmsFlashPromotionService flashPromotionService;

    @ApiOperation(value = "通过活动名称模糊分页查询活动信息")
    @GetMapping("/list")
    public CommonResult getFlashPromotion(@RequestParam(required = false) String keyword,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsFlashPromotion> promotions = flashPromotionService.getFlashPromotion(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(promotions));
    }

    @ApiOperation(value = "通过id获取活动信息")
    @GetMapping("/{id}")
    public CommonResult getFlashPromotionById(@PathVariable Long id) {
        SmsFlashPromotion promotion = flashPromotionService.getById(id);
        return CommonResult.success(promotion);
    }


    @ApiOperation(value = "创建限时活动")
    @PostMapping("/create")
    public CommonResult addFlashPromotion(@RequestBody SmsFlashPromotion flashPromotion) {
        int count = flashPromotionService.addFlashPromotion(flashPromotion);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @PostMapping("/delete/{id}")
    public CommonResult deleteFlashPromotion(@PathVariable Long id) {
        boolean flag = flashPromotionService.removeById(id);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "修改限时活动上下线状态")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        SmsFlashPromotion promotion = new SmsFlashPromotion();
        promotion.setId(id);
        promotion.setStatus(status);
        boolean flag = flashPromotionService.updateById(promotion);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "更新限时活动信息")
    @PostMapping("/update/{id}")
    public CommonResult updateFlashPromotion(@PathVariable Long id, @RequestBody SmsFlashPromotion flashPromotion) {
        flashPromotion.setId(id);
        boolean flag = flashPromotionService.updateById(flashPromotion);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

}
