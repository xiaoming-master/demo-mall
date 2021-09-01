package com.ming.mall.mapper;

import com.ming.mall.model.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {

    /**
     * 根据id获取商品信息
     * @param productId
     * @return
     */
    PmsProduct getProductById(@Param(value = "productId") Long productId);
}
