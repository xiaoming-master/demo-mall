package com.ming.mall.dto;

import com.ming.mall.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-30 13:36
 * @program: demo-mall
 */
@ApiModel(value = "PmsProductParam", description = "添加商品参数封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PmsProductParam extends PmsProduct {

    @ApiModelProperty(value = "商品会员价格")
    private List<PmsMemberPrice> memberPriceList;

    @ApiModelProperty(value = "优选商品关系")
    private List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList;

    @ApiModelProperty(value = "产品参数信息")
    private List<PmsProductAttributeValue> productAttributeValueList;

    @ApiModelProperty(value = "满减策略")
    private List<PmsProductFullReduction> productFullReductionList;

    @ApiModelProperty(value = "产品阶梯价格")
    private List<PmsProductLadder> productLadderList;

    @ApiModelProperty(value = "sku库存")
    private List<PmsSkuStock> skuStockList;

    @ApiModelProperty(value = "专题商品")
    private List<CmsSubjectProductRelation> subjectProductRelationList;
}
