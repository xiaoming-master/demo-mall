package com.ming.mall.service;

import com.ming.mall.dto.PmsProductParam;
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


    /**
     * 创建商品
     * @param productParam
     * @return
     */

    int addProduct(PmsProductParam productParam);

    /**
     * 更新商品西信息
     * @param productId
     * @param productParam
     * @return
     */
    int updateProduct(Long productId, PmsProductParam productParam);

    /**
     * 批量修改审核状态
     * @param detail
     * @param ids
     * @param verifyStatus
     * @param adminName
     * @return
     */
    int updateVerifyStatus(String detail, List<Long> ids, Integer verifyStatus, String adminName);

    /**
     * 根据id获取商品信息
     * @param productId
     * @return
     */
    PmsProduct getProductById(Long productId);
}
