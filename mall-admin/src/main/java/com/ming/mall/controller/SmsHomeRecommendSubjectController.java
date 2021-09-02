package com.ming.mall.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.SmsHomeRecommendSubject;
import com.ming.mall.service.ISmsHomeRecommendSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "SmsHomeRecommendSubjectController", description = "首页专题推荐")
@RestController
@RequestMapping("/home/recommendSubject")
public class SmsHomeRecommendSubjectController {

    @Autowired
    private ISmsHomeRecommendSubjectService homeRecommendSubjectService;


    @ApiOperation(value = "添加首页主题")
    @PostMapping("/create")
    public CommonResult addRecommendSubject(@RequestBody List<SmsHomeRecommendSubject> homeBrandList) {
        for (SmsHomeRecommendSubject recommendSubject : homeBrandList) {
            recommendSubject.setSort(0);
            recommendSubject.setRecommendStatus(1);
        }
        boolean flag = homeRecommendSubjectService.saveBatch(homeBrandList);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "根据专题名称和推荐状态分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectName", value = "模糊主题名称"),
            @ApiImplicitParam(name = "recommendStatus", value = "推荐状态")
    })
    @GetMapping("/list")
    public CommonResult getRecommendSubject(@RequestParam(required = false) String subjectName,
                                            @RequestParam(required = false) Integer recommendStatus,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<SmsHomeRecommendSubject> subjects = homeRecommendSubjectService.getRecommendSubject(subjectName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(subjects));
    }

    @ApiOperation(value = "批量删除首页推荐主题")
    @PostMapping("/delete")
    public CommonResult deleteRecommendSubject(@RequestParam List<Long> ids) {
        boolean flag = homeRecommendSubjectService.removeByIds(ids);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "批量修改推荐状态")
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus) {
        boolean flag = false;
        if (CollUtil.isNotEmpty(ids)) {
            SmsHomeRecommendSubject recommendSubject = new SmsHomeRecommendSubject();
            recommendSubject.setRecommendStatus(recommendStatus);
            QueryWrapper<SmsHomeRecommendSubject> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", ids);
            flag = homeRecommendSubjectService.update(recommendSubject, queryWrapper);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }


    @ApiOperation(value = "修改排序")
    @PostMapping("/update/sort/{id}")
    public CommonResult updateSort(@PathVariable Long id, @RequestParam(required = false) Integer sort) {
        boolean flag = false;
        if (sort != null) {
            SmsHomeRecommendSubject recommendSubject = new SmsHomeRecommendSubject();
            recommendSubject.setSort(sort);
            recommendSubject.setId(id);
            flag = homeRecommendSubjectService.updateById(recommendSubject);
        }
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }
}
