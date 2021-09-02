package com.ming.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.SmsHomeBrand;
import com.ming.mall.service.ISmsHomeBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsHomeBrandController", description = "首页品牌推荐")
@RestController
@RequestMapping("/home/brand")
public class SmsHomeBrandController {

    @Autowired
    private ISmsHomeBrandService homeBrandService;

    @ApiOperation(value = "添加首页推荐品牌")
    @PostMapping("/create")
    public CommonResult addRecommendBrand(@RequestBody List<SmsHomeBrand> homeBrandList) {

        for (SmsHomeBrand brand : homeBrandList) {
            brand.setRecommendStatus(1);
            brand.setSort(0);
        }
        boolean flag = homeBrandService.saveBatch(homeBrandList);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "根据品牌名称或推荐状态模糊分页查询")
    @GetMapping("/list")
    public CommonResult getRecommendBran(@RequestParam(required = false) String brandName,
                                         @RequestParam(required = false) Integer recommendStatus,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeBrand> brands = homeBrandService.getRecommendBran(brandName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brands));
    }

    @ApiOperation(value = "批量删除首页推荐品牌")
    @PostMapping("/delete")
    public CommonResult deleteRecommendBrand(@RequestParam List<Long> ids) {
        boolean flag = homeBrandService.removeByIds(ids);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "修改推荐状态")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {

        SmsHomeBrand brand = new SmsHomeBrand();
        brand.setRecommendStatus(recommendStatus);
        QueryWrapper<SmsHomeBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        boolean flag = homeBrandService.update(brand, queryWrapper);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "修改推荐品牌排序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam(required = false) Integer sort) {
        boolean flag = false;
        if (sort != null) {
            SmsHomeBrand brand = new SmsHomeBrand();
            brand.setSort(sort);
            brand.setId(id);
            flag = homeBrandService.updateById(brand);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


}
