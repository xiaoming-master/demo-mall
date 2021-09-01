package com.ming.mall.service;

import com.ming.mall.model.PmsSkuStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku的库存 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IPmsSkuStockService extends IService<PmsSkuStock> {

    /**
     *根据商品id和sku编码模糊查询
     * @param pid 商品id
     * @param keyword sku编码
     * @return
     */
    List<PmsSkuStock> getSkuStockByKeyword(Long pid, String keyword);
}
