package com.ming.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类）
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PmsProductCategoryAttributeRelation对象", description="产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类）")
public class PmsProductCategoryAttributeRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productCategoryId;

    private Long productAttributeId;


}
