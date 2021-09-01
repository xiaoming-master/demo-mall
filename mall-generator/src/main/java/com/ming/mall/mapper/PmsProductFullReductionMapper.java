package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品满减表(只针对同商品) Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductFullReductionMapper extends BaseMapper<PmsProductFullReduction> {

    /**
     * 批量插入
     *
     * @param productFullReductionList
     */
    void insertList(@Param(value = "productFullReductionList") List<PmsProductFullReduction> productFullReductionList);
}
