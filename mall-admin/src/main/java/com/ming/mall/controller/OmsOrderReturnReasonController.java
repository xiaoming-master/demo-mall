package com.ming.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.model.OmsOrderReturnReason;
import com.ming.mall.service.IOmsOrderReturnReasonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退货原因表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "OmsOrderReturnReasonController", description = "退货原因管理")
@RestController
@RequestMapping("/returnReason")
public class OmsOrderReturnReasonController {

    @Autowired
    private IOmsOrderReturnReasonService orderReturnReasonService;

    @ApiOperation(value = "分页获取退货原因")
    @GetMapping("/list")
    public CommonResult getReturnReason(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "5") Integer pageSize) {
        List<OmsOrderReturnReason> returnReasons = orderReturnReasonService.getReturnReason(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(returnReasons));
    }

    @ApiOperation(value = "通过id获取退货原因信息")
    @GetMapping("/{id}")
    public CommonResult getReturnReasonById(@PathVariable Long id){
        OmsOrderReturnReason reason = orderReturnReasonService.getById(id);
        return CommonResult.success(reason);
    }

    @ApiOperation(value = "添加退货原因")
    @PostMapping("/create")
    public CommonResult addReturnReason(@RequestBody OmsOrderReturnReason returnReason) {
        returnReason.setCreateTime(new Date());
        boolean flag = orderReturnReasonService.save(returnReason);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "批量删除退货原因")
    @PostMapping("/delete")
    public CommonResult deleteReturnReason(@RequestParam List<Long> ids) {
        boolean flag = orderReturnReasonService.removeByIds(ids);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "修改退货原因")
    @PostMapping("/update/{id}")
    public CommonResult updateReturnReason(@RequestBody OmsOrderReturnReason returnReason, @PathVariable Long id) {
        returnReason.setId(id);
        boolean flag = orderReturnReasonService.updateById(returnReason);
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

    @ApiOperation(value = "批量修改退货原因启用状态")
    @PostMapping("/update/status")
    public CommonResult updateStatus(@RequestParam List<Long> ids, @RequestParam Integer status) {
        OmsOrderReturnReason reason = new OmsOrderReturnReason();
        reason.setStatus(status);
        boolean flag = orderReturnReasonService.update(reason, new QueryWrapper<OmsOrderReturnReason>().in("id", ids));
        return flag ? CommonResult.success(flag) : CommonResult.failed();
    }

}
