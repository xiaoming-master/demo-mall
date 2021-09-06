package com.ming.mall.service;

import com.ming.mall.dto.OmsOrderReturnApplyParam;
import com.ming.mall.dto.OmsOrderReturnApplyStatusParam;
import com.ming.mall.model.OmsOrderReturnApply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IOmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {

    /**
     * 分页查询退货申请
     * @param applyParam 参数封装
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<OmsOrderReturnApply> getReturnApply(OmsOrderReturnApplyParam applyParam, Integer pageNum, Integer pageSize);


    /**
     * 批量删除退货申请
     * @param ids
     * @return
     */
    int deleteReturnApply(List<Long> ids);

    /**
     * 修改退货申请状态
     * @param statusParam
     * @param id
     * @return
     */
    int updateStatus(OmsOrderReturnApplyStatusParam statusParam, Long id);
}
