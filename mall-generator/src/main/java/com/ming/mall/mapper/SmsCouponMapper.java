package com.ming.mall.mapper;

import com.ming.mall.dto.SmsCouponParam;
import com.ming.mall.model.SmsCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface SmsCouponMapper extends BaseMapper<SmsCoupon> {

    /**
     * 通过优惠券id获取优惠券信息
     * @param couponId
     * @return
     */
    SmsCouponParam getCouponById(@Param(value = "couponId") Long couponId);
}
