package com.ming.mall.service.impl;

import com.ming.mall.model.OmsOrderItem;
import com.ming.mall.mapper.OmsOrderItemMapper;
import com.ming.mall.service.IOmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements IOmsOrderItemService {

}
