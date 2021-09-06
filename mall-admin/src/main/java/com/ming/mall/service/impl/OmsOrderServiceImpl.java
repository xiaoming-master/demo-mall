package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.*;
import com.ming.mall.mapper.OmsOrderMapper;
import com.ming.mall.mapper.OmsOrderOperateHistoryMapper;
import com.ming.mall.model.OmsOrder;
import com.ming.mall.model.OmsOrderOperateHistory;
import com.ming.mall.service.IOmsOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderServiceImpl.class);

    @Autowired
    private OmsOrderMapper orderMapper;

    @Autowired
    private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;


    /**
     * 通过条件分页查询订单
     *
     * @param orderParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OmsOrder> getOrderByKeyword(OmsOrderParam orderParam, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<OmsOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_status", 0);
        queryWrapper.eq(StrUtil.isNotEmpty(orderParam.getOrderSn()), "order_sn", orderParam.getOrderSn());
        queryWrapper.eq(orderParam.getStatus() != null, "status", orderParam.getStatus());
        queryWrapper.eq(orderParam.getOrderType() != null, "order_type", orderParam.getOrderType());
        queryWrapper.eq(orderParam.getSourceType() != null, "source_type", orderParam.getSourceType());
        queryWrapper.likeRight(orderParam.getCreateTime() != null, "create_time", orderParam.getCreateTime());

        if (StrUtil.isNotEmpty(orderParam.getReceiverKeyword())) {
            QueryWrapper<OmsOrder> receiveQueryWrapper = new QueryWrapper<>();
            receiveQueryWrapper.like("receiver_name", orderParam.getReceiverKeyword());
            receiveQueryWrapper.or(omsOrderQueryWrapper -> {
                omsOrderQueryWrapper.like("receiver_phone", orderParam.getReceiverKeyword());
            });
            queryWrapper.and(omsOrderQueryWrapper -> omsOrderQueryWrapper = receiveQueryWrapper);
        }

        return orderMapper.selectList(queryWrapper);
    }


    /**
     * 批量关闭订单
     * 0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     *
     * @param ids
     * @param note
     * @param principal
     * @return
     */
    @Transactional
    @Override
    public int closeOrder(List<Long> ids, String note, Principal principal) {
        if (CollUtil.isNotEmpty(ids)) {
            //更新订单
            OmsOrder order = new OmsOrder();
            order.setStatus(4);
            order.setNote(note);
            QueryWrapper<OmsOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", ids);
            orderMapper.update(order, queryWrapper);

            //插入操作记录
            ArrayList<OmsOrderOperateHistory> histories = new ArrayList<>();
            for (Long id : ids) {
                OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                //构建操作记录对象
                history.setNote("关闭订单" + note);
                history.setCreateTime(new Date());
                history.setOperateMan(principal.getName());
                history.setOrderId(id);
                history.setOrderStatus(4);

                histories.add(history);
            }
            orderOperateHistoryMapper.insertList(histories);
        }
        return ids.size();
    }


    /**
     * 批量发货
     *
     * @param deliveryParamList
     * @param principal
     * @return
     */
    @Transactional
    @Override
    public int deliveryProduct(List<OmsDeliveryParam> deliveryParamList, Principal principal) {
        //订单
        ArrayList<OmsOrder> orders = new ArrayList<>();
        //操作记录
        ArrayList<OmsOrderOperateHistory> histories = new ArrayList<>();
        for (OmsDeliveryParam param : deliveryParamList) {
            //构建订单
            OmsOrder order = new OmsOrder();
            order.setId(param.getOrderId());
            order.setDeliveryCompany(param.getDeliveryCompany());
            order.setDeliverySn(param.getDeliverySn());
            order.setStatus(2);
            orders.add(order);

            //构建操作记录
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            //0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
            history.setOrderStatus(2);
            history.setOrderId(param.getOrderId());
            history.setOperateMan(principal.getName());
            history.setCreateTime(new Date());
            history.setNote("完成发货");
            histories.add(history);
        }
        //更新订单
        boolean flag = this.updateBatchById(orders);

        //插入操作记录
        orderOperateHistoryMapper.insertList(histories);

        return flag ? deliveryParamList.size() : 0;
    }


    /**
     * 修改订单金额
     *
     * @param moneyInfoParam
     * @param principal
     * @return
     */
    @Transactional
    @Override
    public int updateMoneyInfo(OmsOrderMoneyInfoParam moneyInfoParam, Principal principal) {
        //先查找订单
        Integer count = orderMapper.selectCount(new QueryWrapper<OmsOrder>().eq("id", moneyInfoParam.getOrderId()));
        if (count == null || count <= 0) {//订单不存在
            return -1;
        }
        OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        //设置优惠金额
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        //设置运费
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        //设置修改时间
        order.setModifyTime(new Date());
        //更新
        int res = orderMapper.updateById(order);

        //添加修改操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setCreateTime(new Date());
        history.setNote("修改订单金额");
        history.setOperateMan(principal.getName());
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setOrderStatus(moneyInfoParam.getStatus());
        orderOperateHistoryMapper.insert(history);
        return res;
    }


    /**
     * 添加订单备注
     *
     * @param id
     * @param note
     * @param status
     * @param principal
     * @return
     */
    @Transactional
    @Override
    public int updateNote(Long id, String note, Integer status, Principal principal) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        int count = orderMapper.updateById(order);

        //添加操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderStatus(status);
        history.setCreateTime(new Date());
        history.setOrderId(id);
        history.setNote("修改订单备注" + note);
        history.setOperateMan(principal.getName());
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    /**
     * 修改收货人信息
     *
     * @param receiverInfoParam
     * @param principal
     * @return
     */
    @Transactional
    @Override
    public int updateReceiverInfo(OmsOrderReceiverInfoParam receiverInfoParam, Principal principal) {
        //更新订单
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        int count = orderMapper.updateById(order);

        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOperateMan(principal.getName());
        history.setNote("修改收货人信息");
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOrderStatus(receiverInfoParam.getStatus());
        orderOperateHistoryMapper.insert(history);
        return count;
    }


    /**
     * 根据订单id获取订单信息
     *
     * @param id
     * @return
     */
    @Override
    public OmsOrderDetail getOrderById(Long id) {
        return orderMapper.getOrderById(id);
    }
}
