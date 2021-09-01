package com.ming.mall.mapper;

import com.ming.mall.model.PmsSkuStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sku的库存 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsSkuStockMapper extends BaseMapper<PmsSkuStock> {


    /**
     * 批量插入sku
     * @param skuStockList
     */
    void insertList(@Param(value = "skuStockList") List<PmsSkuStock> skuStockList);
}
