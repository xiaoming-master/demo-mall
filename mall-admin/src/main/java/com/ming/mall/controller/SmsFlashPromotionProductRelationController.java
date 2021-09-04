package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.SmsFlashPromotionProductRelationDetail;
import com.ming.mall.model.SmsFlashPromotionProductRelation;
import com.ming.mall.service.ISmsFlashPromotionProductRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品限时购与商品关系表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(value = "SmsFlashPromotionProductRelationController", description = "限时活动与商品关系管理")
@RestController
@RequestMapping("/flashProductRelation")
public class SmsFlashPromotionProductRelationController {

    @Autowired
    private ISmsFlashPromotionProductRelationService flashPromotionProductRelationService;


    @ApiOperation(value = "通过i获取商品促销信息")
    @GetMapping("/{id}")
    public CommonResult getFlashPromotionRelation(@PathVariable Long id) {
        SmsFlashPromotionProductRelation promotionProductRelation = flashPromotionProductRelationService.getById(id);
        return CommonResult.success(promotionProductRelation);
    }

    @ApiOperation(value = "批量选择添加关联商品")
    @PostMapping("/create")
    public CommonResult addFlashPromotionRelation(@RequestBody List<SmsFlashPromotionProductRelation> relationList) {

        for (SmsFlashPromotionProductRelation relation : relationList) {
            relation.setSort(0);
        }
        boolean flag = flashPromotionProductRelationService.saveBatch(relationList);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "删除关联")
    @PostMapping("/delete/{id}")
    public CommonResult deleteRelation(@PathVariable Long id) {
        boolean flag = flashPromotionProductRelationService.removeById(id);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "分页查询不同场次关联及商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flashPromotionId", value = "限时活动ID"),
            @ApiImplicitParam(name = "getFlashPromotionSession", value = "场次id")
    })
    @GetMapping("/list")
    public CommonResult getFlashPromotionSession(@RequestParam Long flashPromotionId,
                                                 @RequestParam Long flashPromotionSessionId,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsFlashPromotionProductRelationDetail> relations = flashPromotionProductRelationService.getFlashPromotionSession(flashPromotionId, flashPromotionSessionId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(relations));
    }

    @ApiOperation(value = "修改关联信息")
    @PostMapping("/update/{id}")
    public CommonResult updateRelation(@PathVariable Long id, @RequestBody SmsFlashPromotionProductRelation relation) {
        relation.setId(id);
        boolean flag = flashPromotionProductRelationService.updateById(relation);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

}
