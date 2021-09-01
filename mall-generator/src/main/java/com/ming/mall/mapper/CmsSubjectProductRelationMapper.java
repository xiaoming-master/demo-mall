package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专题商品关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface CmsSubjectProductRelationMapper extends BaseMapper<CmsSubjectProductRelation> {

    /**
     * 批量插入
     *
     * @param subjectProductRelationList
     */
    void insertList(@Param(value = "subjectProductRelationList") List<CmsSubjectProductRelation> subjectProductRelationList);

    public CmsSubjectProductRelation getSubjectProductRelationByProductId(@Param(value = "productId") Long productId);
}
