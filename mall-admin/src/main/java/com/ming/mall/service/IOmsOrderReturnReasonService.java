package com.ming.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.mall.model.OmsOrderReturnReason;

import java.util.List;

/**
 * <p>
 * 退货原因表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IOmsOrderReturnReasonService extends IService<OmsOrderReturnReason> {


    /**
     * 分页获取退货原因
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<OmsOrderReturnReason> getReturnReason(Integer pageNum, Integer pageSize);
}
