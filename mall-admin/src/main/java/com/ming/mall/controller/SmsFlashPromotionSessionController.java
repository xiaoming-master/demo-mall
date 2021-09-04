package com.ming.mall.controller;


import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.SmsFlashPromotionSessionDetail;
import com.ming.mall.model.SmsFlashPromotionSession;
import com.ming.mall.service.ISmsFlashPromotionSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 限时购场次表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsFlashPromotionSessionController", description = "限时活动场次管理")
@RestController
@RequestMapping("/flashSession")
public class SmsFlashPromotionSessionController {

    @Autowired
    private ISmsFlashPromotionSessionService flashPromotionSessionService;

    @ApiOperation(value = "添加场次")
    @PostMapping("/create")
    public CommonResult addFlashPromotionSession(@RequestBody SmsFlashPromotionSession promotionSession) {
        promotionSession.setCreateTime(new Date());
        boolean flag = flashPromotionSessionService.save(promotionSession);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "通过id获取活动场次信息")
    @GetMapping("/{id}")
    public CommonResult getFlashPromotionSessionById(@PathVariable Long id) {
        SmsFlashPromotionSession promotionSession = flashPromotionSessionService.getById(id);
        return CommonResult.success(promotionSession);
    }

    @ApiOperation(value = "删除活动场次")
    @PostMapping("/delete/{id}")
    public CommonResult deleteFlashPromotionSession(@PathVariable Long id) {
        boolean flag = flashPromotionSessionService.removeById(id);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "获取所有的活动场次")
    @GetMapping("/list")
    public CommonResult getAllFlashPromotionSession() {
        List<SmsFlashPromotionSession> promotionSessionList = flashPromotionSessionService.list();
        return CommonResult.success(promotionSessionList);
    }

    @ApiOperation(value = "修改限时活动信息")
    @PostMapping("/update/{id}")
    public CommonResult updateFlashPromotionSession(@PathVariable Long id, @RequestParam SmsFlashPromotionSession promotionSession) {
        promotionSession.setId(id);
        boolean flag = flashPromotionSessionService.updateById(promotionSession);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "修改启用状态")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        SmsFlashPromotionSession promotionSession = new SmsFlashPromotionSession();
        promotionSession.setId(id);
        promotionSession.setStatus(status);
        boolean flag = flashPromotionSessionService.updateById(promotionSession);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "根据活动id获取全部场次以及商品的数量")
    @GetMapping("/selectList")
    public CommonResult getFlashPromotionSessionAndProductCount(@RequestParam Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> promotionSessionDetails = flashPromotionSessionService.getFlashPromotionSessionAndProductCount(flashPromotionId);
        return CommonResult.success(promotionSessionDetails);
    }

}
