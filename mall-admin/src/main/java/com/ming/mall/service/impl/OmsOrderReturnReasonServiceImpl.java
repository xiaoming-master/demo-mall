package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.mapper.OmsOrderReturnReasonMapper;
import com.ming.mall.model.OmsOrderReturnReason;
import com.ming.mall.service.IOmsOrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 退货原因表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class OmsOrderReturnReasonServiceImpl extends ServiceImpl<OmsOrderReturnReasonMapper, OmsOrderReturnReason> implements IOmsOrderReturnReasonService {

    @Autowired
    private OmsOrderReturnReasonMapper orderReturnReasonMapper;

    /**
     * 分页获取退货原因
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OmsOrderReturnReason> getReturnReason(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return orderReturnReasonMapper.selectList(new QueryWrapper<OmsOrderReturnReason>());
    }
}
