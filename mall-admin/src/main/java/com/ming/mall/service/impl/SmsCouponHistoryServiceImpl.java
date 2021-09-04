package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ming.mall.model.SmsCouponHistory;
import com.ming.mall.mapper.SmsCouponHistoryMapper;
import com.ming.mall.service.ISmsCouponHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryMapper, SmsCouponHistory> implements ISmsCouponHistoryService {

    @Autowired
    private SmsCouponHistoryMapper couponHistoryMapper;
    /**
     * 通过优惠券id,使用状态，订单编号来查询历史记录信息
     *
     * @param couponId
     * @param orderSn
     * @param useStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsCouponHistory> getCouponHistory(Long couponId, String orderSn, Integer useStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<SmsCouponHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(couponId != null, "coupon_id", couponId);
        queryWrapper.eq(orderSn != null, "order_sn", orderSn);
        queryWrapper.eq(useStatus != null, "use_status", useStatus);
        return couponHistoryMapper.selectList(queryWrapper);
    }
}
