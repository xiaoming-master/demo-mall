package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ming
 * @create: 2021-08-23 20:55
 * @program: demo-mall
 */
@ApiModel(value = "查询商品参数封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQueryParam {

    @ApiModelProperty("商品品牌编号")
    private Long brandId;

    @ApiModelProperty("商品名称模糊关键字")
    private String keyword;

    @ApiModelProperty("商品分类编号")
    private Long productCategoryId;

    @ApiModelProperty("商品货号")
    private String productSn;

    @ApiModelProperty("商品货号")
    private Integer publishStatus;

    @ApiModelProperty("商品审核状态")
    private Integer verifyStatus;

}
