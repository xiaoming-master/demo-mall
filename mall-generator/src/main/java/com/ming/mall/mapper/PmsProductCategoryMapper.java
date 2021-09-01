package com.ming.mall.mapper;

import com.ming.mall.dto.PmsProductCategoryNode;
import com.ming.mall.model.PmsProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    /**
     * 获取商品分类及其子分类
     * @param parentId
     * @return
     */
    List<PmsProductCategoryNode> getProductCategoryWithChildren(@Param(value = "parentId") long parentId);
}
