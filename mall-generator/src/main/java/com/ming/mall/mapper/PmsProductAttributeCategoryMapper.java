package com.ming.mall.mapper;

import com.ming.mall.dto.ProductAttributeCategoryWithAttr;
import com.ming.mall.model.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductAttributeCategoryMapper extends BaseMapper<PmsProductAttributeCategory> {


    /**
     * 获取所有商品属性分类及其下属性
     * @return
     */
    List<ProductAttributeCategoryWithAttr> getAttributeCategoryWithAttr();
}
