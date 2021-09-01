package com.ming.mall.dto;

import com.ming.mall.model.PmsProductCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-28 17:01
 * @program: demo-mall
 */
@Api(value = "PmsProductCategoryParam",description = "商品分类信息封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PmsProductCategoryParam extends PmsProductCategory {
    @ApiModelProperty("产品相关筛选属性集合")
    private List<Long> productAttributeIdList;
}
