package com.ming.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ming
 * @create: 2021-08-28 10:55
 * @program: demo-mall
 */
@ApiModel("商品属性分类id和属性id封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttrInfo {
    @ApiModelProperty("商品属性分类id")
    private Long attributeCategoryId;
    @ApiModelProperty("商品属性id")
    private Long attributeId;
}
