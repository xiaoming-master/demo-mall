package com.ming.mall.mapper;

import com.ming.mall.model.PmsMemberPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品会员价格表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsMemberPriceMapper extends BaseMapper<PmsMemberPrice> {

    /**
     * 批量插入商品会员价格
     * @param memberPriceList
     */
    void insertList(@Param(value = "memberPriceList") List<PmsMemberPrice> memberPriceList);
}
