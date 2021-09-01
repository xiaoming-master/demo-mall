package com.ming.mall.mapper;

import com.ming.mall.model.CmsPrefrenceAreaProductRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优选专区和产品关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface CmsPrefrenceAreaProductRelationMapper extends BaseMapper<CmsPrefrenceAreaProductRelation> {

    /**
     * 批量插入优选产品关系
     *
     * @param prefrenceAreaProductRelationList
     */
    void insertList(@Param(value = "prefrenceAreaProductRelationList") List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList);

    CmsPrefrenceAreaProductRelation getPrefrenceAreaProductRelationByProductId(@Param(value = "productId") Long productId);
}
