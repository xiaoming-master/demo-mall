package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.mapper.CmsSubjectMapper;
import com.ming.mall.model.CmsSubject;
import com.ming.mall.service.ICmsSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 专题表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "CmsSubjectController",description = "商品专题管理")
@RestController
@RequestMapping("/subject")
public class CmsSubjectController {

    @Autowired
    private ICmsSubjectService subjectService;

    @ApiOperation(value = "获取所有专题")
    @GetMapping("/listAll")
    public CommonResult getAllSubject() {
        List<CmsSubject> subjects = subjectService.list();
        return CommonResult.success(subjects);
    }

    @ApiOperation(value = "根据专题名称分页获取专题")
    @GetMapping("/list")
    public CommonResult getSubjectByKeyword(@RequestParam(required = false) String keyword,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        List<CmsSubject> subjects = subjectService.getSubjectByKeyword(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(subjects));
    }

}
