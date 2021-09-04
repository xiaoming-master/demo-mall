package com.ming.mall.service;

import com.ming.mall.model.SmsCouponHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsCouponHistoryService extends IService<SmsCouponHistory> {

    /**
     * 通过优惠券id,使用状态，订单编号来查询历史记录信息
     * @param couponId
     * @param orderSn
     * @param useStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsCouponHistory> getCouponHistory(Long couponId, String orderSn, Integer useStatus, Integer pageNum, Integer pageSize);
}
