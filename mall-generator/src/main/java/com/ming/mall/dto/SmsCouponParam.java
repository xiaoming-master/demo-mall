package com.ming.mall.dto;

import com.ming.mall.model.SmsCoupon;
import com.ming.mall.model.SmsCouponProductCategoryRelation;
import com.ming.mall.model.SmsCouponProductRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-09-02 15:45
 * @program: demo-mall
 */
@ApiModel(value = "SmsCouponParam", description = "商品优惠券参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class SmsCouponParam extends SmsCoupon {

    @ApiModelProperty(value = "指定商品分类", required = false)
    private List<SmsCouponProductCategoryRelation> productCategoryRelationList;

    @ApiModelProperty(value = "指定商品", required = false)
    private List<SmsCouponProductRelation> productRelationList;
}
