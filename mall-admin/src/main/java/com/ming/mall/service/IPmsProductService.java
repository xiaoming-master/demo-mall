package com.ming.mall.service;

import com.ming.mall.dto.ProductQueryParam;
import com.ming.mall.model.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IPmsProductService extends IService<PmsProduct> {

    /**
     * 模糊分页查询商品
     * @param queryParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProduct> getProduct(ProductQueryParam queryParam, Integer pageNum, Integer pageSize);
}
