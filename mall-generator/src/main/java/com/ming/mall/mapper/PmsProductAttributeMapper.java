package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.dto.AttrInfo;
import com.ming.mall.model.PmsProductAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductAttributeMapper extends BaseMapper<PmsProductAttribute> {

    /**
     * 通过商品分类id获取属性以及分类信息
     *
     * @param productCategoryId
     * @return
     */
    List<AttrInfo> getAttributeInfoByProductCateGoryId(@Param(value = "productCategoryId") Long productCategoryId);

}
