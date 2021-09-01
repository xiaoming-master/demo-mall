package com.ming.mall.mapper;

import com.ming.mall.model.PmsProductAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 存储产品参数信息的表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductAttributeValueMapper extends BaseMapper<PmsProductAttributeValue> {

    /**
     * 批量插入产品参数信息
     * @param productAttributeValueList
     */
    void insertList(@Param(value = "productAttributeValueList") List<PmsProductAttributeValue> productAttributeValueList);
}
