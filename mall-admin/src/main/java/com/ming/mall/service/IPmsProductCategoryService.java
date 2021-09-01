package com.ming.mall.service;

import com.ming.mall.dto.PmsProductCategoryNode;
import com.ming.mall.dto.PmsProductCategoryParam;
import com.ming.mall.model.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IPmsProductCategoryService extends IService<PmsProductCategory> {

    /**
     * 添加商品分类信息
     * @param pmsProductCategoryParam
     * @return
     */
    int addProductCategory(PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * 通过父id获取分类信息
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProductCategory> getProductCategoryByParentId(Long parentId, Integer pageNum, Integer pageSize);

    /**
     * 获取商品分类及其子分类
     * @return
     */
    List<PmsProductCategoryNode> getProductCategoryWithChildren();


    /**
     * 更新商品分类信息
     * @param id
     * @param productCategoryParam
     * @return
     */
    int updateProductCategory(Long id, PmsProductCategoryParam productCategoryParam);
}
