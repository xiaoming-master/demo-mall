package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Author: ming
 * @create: 2021-09-05 15:32
 * @program: demo-mall
 */
@ApiModel(value = "OmsOrderMoneyInfoParam", description = "修改订单费用参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class OmsOrderMoneyInfoParam {

    @ApiModelProperty(value = "折扣金额")
    private BigDecimal discountAmount=BigDecimal.ZERO;

    @ApiModelProperty(value = "运费金额")
    private BigDecimal freightAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单状态: 0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;

}
