package com.ming.mall.controller;


import com.ming.mall.common.api.CommonPage;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.OmsOrderReturnApplyParam;
import com.ming.mall.dto.OmsOrderReturnApplyStatusParam;
import com.ming.mall.model.OmsOrderReturnApply;
import com.ming.mall.service.IOmsOrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Api(tags = "OmsOrderReturnApplyController", description = "退货申请管理")
@RestController
@RequestMapping("/returnApply")
public class OmsOrderReturnApplyController {

    @Autowired
    private IOmsOrderReturnApplyService orderReturnApplyService;

    @ApiOperation(value = "分页获取退货申请")
    @GetMapping("/list")
    public CommonResult getReturnApply(OmsOrderReturnApplyParam applyParam, Integer pageNum, Integer pageSize) {
        List<OmsOrderReturnApply> applies = orderReturnApplyService.getReturnApply(applyParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(applies));
    }

    @ApiOperation(value = "批量删除退货申请")
    @PostMapping("/delete")
    public CommonResult deleteReturnApply(@RequestParam List<Long> ids) {
        int count = orderReturnApplyService.deleteReturnApply(ids);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @ApiOperation(value = "通过id获取退货申请")
    @GetMapping("/{id}")
    public CommonResult getReturnApplyById(@PathVariable Long id) {
        OmsOrderReturnApply apply = orderReturnApplyService.getById(id);
        return CommonResult.success(apply);
    }

    @ApiOperation(value = "修改订单状态")
    @PostMapping("/update/status/{id}")
    public CommonResult updateStatus(@RequestBody OmsOrderReturnApplyStatusParam statusParam, @PathVariable Long id) {
        int count = orderReturnApplyService.updateStatus(statusParam, id);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }
}
