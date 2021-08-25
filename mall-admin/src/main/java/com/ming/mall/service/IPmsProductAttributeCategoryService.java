package com.ming.mall.service;

import com.ming.mall.dto.ProductAttributeCategoryWithAttr;
import com.ming.mall.model.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IPmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    /**
     * 分页获取商品属性分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProductAttributeCategory> getAttributeCategory(Integer pageNum, Integer pageSize);

    /**
     * 获取所有商品属性分类及其下属性
     * @return
     */
    List<ProductAttributeCategoryWithAttr> getAttributeCategoryWithAttr();
}
