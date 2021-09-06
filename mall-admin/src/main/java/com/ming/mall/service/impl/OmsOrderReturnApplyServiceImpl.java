package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.OmsOrderReturnApplyParam;
import com.ming.mall.dto.OmsOrderReturnApplyStatusParam;
import com.ming.mall.mapper.OmsOrderReturnApplyMapper;
import com.ming.mall.model.OmsOrderReturnApply;
import com.ming.mall.service.IOmsOrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class OmsOrderReturnApplyServiceImpl extends ServiceImpl<OmsOrderReturnApplyMapper, OmsOrderReturnApply> implements IOmsOrderReturnApplyService {

    @Autowired
    private OmsOrderReturnApplyMapper orderReturnApplyMapper;

    /**
     * 分页查询退货申请
     *
     * @param applyParam 参数封装
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OmsOrderReturnApply> getReturnApply(OmsOrderReturnApplyParam applyParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<OmsOrderReturnApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(applyParam.getId() != null, "id", applyParam.getId());
        queryWrapper.eq(applyParam.getStatus() != null, "status", applyParam.getStatus());
        queryWrapper.eq(StrUtil.isNotEmpty(applyParam.getHandleMan()), "handle_man", applyParam.getHandleMan());
        queryWrapper.likeRight(StrUtil.isNotEmpty(applyParam.getCreateTime()), "create_time", applyParam.getCreateTime());
        queryWrapper.likeRight(StrUtil.isNotEmpty(applyParam.getHandleTime()), "handle_time", applyParam.getHandleTime());
        if (StrUtil.isNotEmpty(applyParam.getReceiverKeyword())) {
            QueryWrapper<OmsOrderReturnApply> receiverQueryWrapper = new QueryWrapper<>();
            receiverQueryWrapper.like("handle_man", applyParam.getReceiverKeyword()).or(new Consumer<QueryWrapper<OmsOrderReturnApply>>() {
                @Override
                public void accept(QueryWrapper<OmsOrderReturnApply> wrapper) {
                    wrapper.like("return_phone", applyParam.getReceiverKeyword());
                }
            });

            queryWrapper.and(wrapper -> wrapper = receiverQueryWrapper);
        }

        return orderReturnApplyMapper.selectList(queryWrapper);
    }


    /**
     * 批量删除退货申请
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteReturnApply(List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            QueryWrapper<OmsOrderReturnApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", 3).in("id", ids);
            return orderReturnApplyMapper.delete(queryWrapper);
        }
        return 0;
    }

    /**
     * 修改退货申请状态
     *
     * @param statusParam
     * @param id
     * @return
     */
    @Override
    public int updateStatus(OmsOrderReturnApplyStatusParam statusParam, Long id) {

        OmsOrderReturnApply apply = new OmsOrderReturnApply();

        if (statusParam.getStatus().equals(1)) {//确认退货
            apply.setStatus(1);
            apply.setId(id);
            apply.setHandleTime(new Date());
            apply.setHandleMan(statusParam.getHandleMan());
            apply.setHandleNote(statusParam.getHandleNote());
            apply.setReturnAmount(statusParam.getReturnAmount());
            apply.setCompanyAddressId(statusParam.getCompanyAddressId());
        } else if (statusParam.getStatus().equals(2)) {//完成退货
            apply.setStatus(2);
            apply.setId(id);
            apply.setReceiveTime(new Date());
            apply.setReceiveMan(statusParam.getReceiveMan());
            apply.setReceiveNote(statusParam.getReceiveNote());
        } else if (statusParam.getStatus().equals(3)) {//拒绝退货
            apply.setId(id);
            apply.setStatus(3);
            apply.setHandleTime(new Date());
            apply.setHandleNote(statusParam.getHandleNote());
            apply.setHandleMan(statusParam.getHandleMan());
        } else {
            return 0;
        }
        return orderReturnApplyMapper.updateById(apply);
    }
}
