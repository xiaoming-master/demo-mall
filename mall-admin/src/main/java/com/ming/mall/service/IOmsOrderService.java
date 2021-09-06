package com.ming.mall.service;

import com.ming.mall.dto.*;
import com.ming.mall.model.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IOmsOrderService extends IService<OmsOrder> {

    /**
     * 通过条件分页查询订单
     * @param orderParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<OmsOrder> getOrderByKeyword(OmsOrderParam orderParam, Integer pageNum, Integer pageSize);


    /**
     * 批量关闭订单
     * @param ids
     * @param note
     * @param principal
     * @return
     */
    int closeOrder(List<Long> ids, String note, Principal principal);


    /**
     * 批量发货
     * @param deliveryParamList
     * @param principal
     * @return
     */
    int deliveryProduct(List<OmsDeliveryParam> deliveryParamList, Principal principal);


    /**
     * 修改订单金额
     * @param moneyInfoParam
     * @param principal
     * @return
     */
    int updateMoneyInfo(OmsOrderMoneyInfoParam moneyInfoParam, Principal principal);

    /**
     * 添加订单备注
     * @param id
     * @param note
     * @param status
     * @param principal
     * @return
     */
    int updateNote(Long id, String note, Integer status, Principal principal);

    /**
     * 修改收货人信息
     * @param receiverInfoParam
     * @param principal
     * @return
     */
    int updateReceiverInfo(OmsOrderReceiverInfoParam receiverInfoParam, Principal principal);

    /**
     * 根据订单id获取订单信息
     * @param id
     * @return
     */
    OmsOrderDetail getOrderById(Long id);
}
