package com.ming.mall.mapper;

import com.ming.mall.model.SmsCouponProductCategoryRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券和产品分类关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface SmsCouponProductCategoryRelationMapper extends BaseMapper<SmsCouponProductCategoryRelation> {

    /**
     * 批量插入
     * @param productCategoryRelationList
     * @return
     */
    int insertList(@Param(value = "productCategoryRelationList") List<SmsCouponProductCategoryRelation> productCategoryRelationList);
}
