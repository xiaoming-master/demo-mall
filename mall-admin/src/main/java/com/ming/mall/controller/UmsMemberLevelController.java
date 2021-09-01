package com.ming.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.UmsMemberLevel;
import com.ming.mall.service.IUmsMemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.smartcardio.CommandAPDU;
import java.util.List;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "UmsMemberLevelController",description = "会员等级管理")
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Autowired
    private IUmsMemberLevelService memberLevelService;

    @ApiOperation(value = "查询所有会员等级")
    @GetMapping("/list")
    public CommonResult getAllMemberLevel(@RequestParam Integer defaultStatus) {
        List<UmsMemberLevel> memberLevels = memberLevelService.list(new QueryWrapper<UmsMemberLevel>().eq("default_status", defaultStatus));
        return CommonResult.success(memberLevels);
    }
}
