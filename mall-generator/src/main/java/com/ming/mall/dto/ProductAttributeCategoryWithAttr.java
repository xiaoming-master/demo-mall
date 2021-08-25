package com.ming.mall.dto;

import com.ming.mall.model.PmsProductAttribute;
import com.ming.mall.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-24 22:05
 * @program: demo-mall
 */
@ApiModel(value = "商品属性分类及其下属性")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductAttributeCategoryWithAttr extends PmsProductAttributeCategory {

    @ApiModelProperty(value = "属性列表")
    private List<PmsProductAttribute> productAttributeList;
}
