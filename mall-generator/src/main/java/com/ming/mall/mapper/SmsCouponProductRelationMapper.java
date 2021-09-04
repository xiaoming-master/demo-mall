package com.ming.mall.mapper;

import com.ming.mall.model.SmsCouponProductRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券和产品的关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface SmsCouponProductRelationMapper extends BaseMapper<SmsCouponProductRelation> {

    /**
     * 批量插入
     * @param productRelationList
     * @return
     */
    int insertList(@Param(value = "productRelationList") List<SmsCouponProductRelation> productRelationList);
}
