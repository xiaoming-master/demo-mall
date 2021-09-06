package com.ming.mall.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ming.mall.model.OmsOrder;
import com.ming.mall.model.OmsOrderItem;
import com.ming.mall.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-09-05 17:29
 * @program: demo-mall
 */
@ApiModel(value = "OmsOrderDetail", description = "订单详细内容")
@Data
@EqualsAndHashCode(callSuper = false)
public class OmsOrderDetail extends OmsOrder {

    @ApiModelProperty(value = "对订单的操作记录")
    @TableField(exist = false)
    private List<OmsOrderOperateHistory> historyList;

    @ApiModelProperty(value = "订单包含的商品")
    @TableField(exist = false)
    private List<OmsOrderItem> orderItemList;


}
