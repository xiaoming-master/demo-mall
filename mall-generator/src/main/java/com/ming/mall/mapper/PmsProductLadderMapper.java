package com.ming.mall.mapper;

import com.ming.mall.model.PmsProductLadder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductLadderMapper extends BaseMapper<PmsProductLadder> {


    /**
     * 批量插入产品阶梯价格
     * @param productLadderList
     */
    void insertList(@Param(value = "productLadderList") List<PmsProductLadder> productLadderList);
}
