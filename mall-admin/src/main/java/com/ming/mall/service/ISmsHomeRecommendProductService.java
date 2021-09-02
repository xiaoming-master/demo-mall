package com.ming.mall.service;

import com.ming.mall.model.SmsHomeRecommendProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsHomeRecommendProductService extends IService<SmsHomeRecommendProduct> {

    /**
     *根据商品名称或推荐状态模糊分页查询
     * @param productName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsHomeRecommendProduct> getRecommendProduct(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize);
}
