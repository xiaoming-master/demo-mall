package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: ming
 * @create: 2021-09-05 16:59
 * @program: demo-mall
 */
@ApiModel(value = "OmsOrderReceiverInfoParam", description = "修改订单收货人信息")
@Data
@EqualsAndHashCode(callSuper = false)
public class OmsOrderReceiverInfoParam {

    @ApiModelProperty(value = "订单id")
    @NotEmpty
    private Long orderId;

    @ApiModelProperty(value = "省份")
    @NotEmpty
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    @NotEmpty
    private String receiverCity;

    @ApiModelProperty(value = "区")
    @NotEmpty
    private String receiverRegion;

    @ApiModelProperty(value = "收货人详细地址")
    @NotEmpty
    private String receiverDetailAddress;

    @ApiModelProperty(value = "收货人名字")
    @NotEmpty
    private String receiverName;

    @ApiModelProperty(value = "收货人联系方式")
    @NotEmpty
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    @NotEmpty
    private String receiverPostCode;

    @ApiModelProperty(value = "订单状态")
    @NotEmpty
    private Integer status;
}
