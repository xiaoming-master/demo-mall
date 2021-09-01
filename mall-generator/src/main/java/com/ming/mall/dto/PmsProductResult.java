package com.ming.mall.dto;

import com.ming.mall.dto.PmsProductParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: ming
 * @create: 2021-09-01 14:06
 * @program: demo-mall
 */
@ApiModel(value = "PmsProductResult", description = "查询商品返回结果信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PmsProductResult extends PmsProductParam {

    @ApiModelProperty(value = "商品分类父id")
    private Long cateParentId;
}
