package com.ming.mall.mapper;

import com.ming.mall.model.PmsProductCategoryAttributeRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类） Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductCategoryAttributeRelationMapper extends BaseMapper<PmsProductCategoryAttributeRelation> {

    /**
     * 批量插入分类与属性的关系
     * @param categoryId
     * @param attributeIdList
     */
    int addAllRelations(@Param(value = "categoryId") Long categoryId,@Param(value = "attributeIdList") List<Long> attributeIdList);
}
