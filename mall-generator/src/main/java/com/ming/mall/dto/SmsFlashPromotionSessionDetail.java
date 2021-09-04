package com.ming.mall.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ming.mall.model.SmsFlashPromotionSession;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: ming
 * @create: 2021-09-03 15:57
 * @program: demo-mall
 */
@ApiModel(value = "SmsFlashPromotionSessionDetail", description = "限时活动场次以及商品总数")
@Data
@EqualsAndHashCode(callSuper = false)
public class SmsFlashPromotionSessionDetail extends SmsFlashPromotionSession {
    @ApiModelProperty(value = "商品总数")
    @TableField(exist = false)
    private Long productCount;
}
