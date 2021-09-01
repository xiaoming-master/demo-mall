package com.ming.mall.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ming.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ming
 * @create: 2021-08-29 11:33
 * @program: demo-mall
 */
@ApiModel(value = "PmsProductCategoryNode", description = "商品分类结点")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PmsProductCategoryNode extends PmsProductCategory {

    @ApiModelProperty(value = "子分类")
    @TableField(exist = false)
    List<PmsProductCategoryNode> children;

}
