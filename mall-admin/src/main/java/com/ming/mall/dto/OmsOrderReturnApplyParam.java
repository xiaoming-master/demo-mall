package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: ming
 * @create: 2021-09-06 13:57
 * @program: demo-mall
 */
@ApiModel(value = "OmsOrderReturnApplyParam", description = "查询退货申请参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class OmsOrderReturnApplyParam {

    @ApiModelProperty(value = "服务单号")
    private Long id;

    @ApiModelProperty(value = "申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝")
    private Integer status;

    @ApiModelProperty(value = "处理人员")
    private String handleMan;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "处理时间")
    private String handleTime;

    @ApiModelProperty(value = "收件人姓名或号码")
    private String receiverKeyword;


}
