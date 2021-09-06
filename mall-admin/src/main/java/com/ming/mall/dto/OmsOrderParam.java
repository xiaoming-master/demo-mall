package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Null;

/**
 * @Author: ming
 * @create: 2021-09-04 09:55
 * @program: demo-mall
 */
@ApiModel(value = "OmsOrderParam", description = "查询订单参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class OmsOrderParam {

    @ApiModelProperty(value = "订单提交时间")
    @Null
    private String createTime;

    @ApiModelProperty(value = "订单编号")
    @Null
    private String orderSn;

    @ApiModelProperty(value = "订单类型：0->正常订单，1->秒杀订单")
    @Null
    private Integer orderType;

    @ApiModelProperty(value = "收货人姓名/号码")
    @Null
    private String receiverKeyword;

    @ApiModelProperty(value = "订单来源")
    @Null
    private Integer sourceType;

    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    @Null
    private Integer status;


}
