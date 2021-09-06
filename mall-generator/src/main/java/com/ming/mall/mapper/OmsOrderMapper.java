package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.dto.OmsOrderDetail;
import com.ming.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {

    /**
     * 根据订单id获取订单信息
     *
     * @param orderId
     * @return
     */
    OmsOrderDetail getOrderById(@Param(value = "orderId") Long orderId);
}
