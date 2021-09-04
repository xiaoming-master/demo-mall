package com.ming.mall.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ming.mall.model.PmsProduct;
import com.ming.mall.model.SmsFlashPromotionProductRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: ming
 * @create: 2021-09-03 17:32
 * @program: demo-mall
 */

@ApiModel(value = "SmsFlashPromotionProductRelationDetail", description = "限时活动场次，活动，商品封装")
@Data
@EqualsAndHashCode(callSuper = false)
public class SmsFlashPromotionProductRelationDetail extends SmsFlashPromotionProductRelation {

    @ApiModelProperty(value = "商品信息")
    @TableField(exist = false)
    private PmsProduct product;

}
