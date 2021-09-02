package com.ming.mall.service;

import com.ming.mall.model.SmsHomeNewProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsHomeNewProductService extends IService<SmsHomeNewProduct> {

    /**
     * 根据推荐状态或商品名称分页模糊查询推荐新品
     * @param productName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsHomeNewProduct> getCommendProduct(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize);
}
