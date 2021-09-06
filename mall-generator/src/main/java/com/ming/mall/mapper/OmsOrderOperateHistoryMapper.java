package com.ming.mall.mapper;

import com.ming.mall.model.OmsOrderOperateHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单操作历史记录 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface OmsOrderOperateHistoryMapper extends BaseMapper<OmsOrderOperateHistory> {

    /**
     * 批量插入操作记录
     * @param histories
     * @return
     */
    int insertList(@Param(value = "histories") List<OmsOrderOperateHistory> histories);
}
