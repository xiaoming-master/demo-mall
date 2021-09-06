package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ming
 * @create: 2021-09-06 14:42
 * @program: demo-mall
 */
@ApiModel(value = "OmsOrderReturnApplyStatusParam", description = "修改退货申请状态参数")
@Data
public class OmsOrderReturnApplyStatusParam {

    @ApiModelProperty(value = "退货申请工单id")
    private Long id;

    @ApiModelProperty(value = "收货地址id")
    private Long companyAddressId;

    @ApiModelProperty(value = "处理人")
    private String handleMan;

    @ApiModelProperty(value = "备注")
    private String handleNote;

    @ApiModelProperty(value = "收货人")
    private String receiveMan;

    @ApiModelProperty(value = "收货备注")
    private String receiveNote;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal returnAmount;

    @ApiModelProperty(value = "申请状态:1->退货中；2->已完成；3->已拒绝")
    private Integer status;
}
