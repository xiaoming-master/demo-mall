package com.ming.mall.service;

import com.ming.mall.dto.AttrInfo;
import com.ming.mall.dto.PmsProductAttributeParam;
import com.ming.mall.model.PmsProductAttribute;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IPmsProductAttributeService extends IService<PmsProductAttribute> {


    /**
     * 通过商品分类id获取属性以及分类信息
     * @param productCategoryId
     * @return
     */
    List<AttrInfo> getAttributeInfoByProductCateGoryId(Long productCategoryId);


    /**
     * 添加商品属性
     * @param productAttributeParam
     * @return
     */
    int addProductAttribute(PmsProductAttributeParam productAttributeParam);

    /**
     * 批量删除商品属性
     * @param ids
     * @return
     */
    int deleteProductAttributeByIds(List<Integer> ids);

    /**
     * 根据分类属性查询分类信息
     * @param cid
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    List<PmsProductAttribute> getProductAttribute(Long cid, Integer pageNum, Integer pageSize, Integer type);
}
