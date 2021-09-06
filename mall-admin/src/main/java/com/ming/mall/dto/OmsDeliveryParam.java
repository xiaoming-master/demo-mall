package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: ming
 * @create: 2021-09-05 15:09
 * @program: demo-mall
 */
@ApiModel(value = "OmsDeliveryParam", description = "发货参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class OmsDeliveryParam {

    @ApiModelProperty(value = "物流公司")
    @NotEmpty
    private String deliveryCompany;

    @ApiModelProperty(value = "物流单号")
    @NotEmpty
    private String deliverySn;

    @ApiModelProperty("订单编号")
    @NotEmpty
    private Long orderId;
}
