package com.ming.mall.service;

import com.ming.mall.dto.SmsCouponParam;
import com.ming.mall.model.SmsCoupon;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsCouponService extends IService<SmsCoupon> {

    /**
     * 根据优惠券名称或类型分页模糊查询
     * @param name
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsCoupon> getCoupon(String name, Integer type, Integer pageNum, Integer pageSize);

    /**
     * 添加优惠券
     * @param couponParam
     * @return
     */
    int addCoupon(SmsCouponParam couponParam);


    /**
     * 通过优惠券id获取优惠券信息
     * @param couponId
     * @return
     */
    SmsCouponParam getCouponById(Long couponId);

    /**
     * 修改优惠券信息
     *
     * @param couponId
     * @param couponParam
     * @return
     */
    int updateCoupon(Long couponId, SmsCouponParam couponParam);
}
