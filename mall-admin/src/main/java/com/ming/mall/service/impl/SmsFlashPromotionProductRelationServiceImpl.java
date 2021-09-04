package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.SmsFlashPromotionProductRelationDetail;
import com.ming.mall.mapper.SmsFlashPromotionProductRelationMapper;
import com.ming.mall.model.SmsFlashPromotionProductRelation;
import com.ming.mall.service.ISmsFlashPromotionProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品限时购与商品关系表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsFlashPromotionProductRelationServiceImpl extends ServiceImpl<SmsFlashPromotionProductRelationMapper, SmsFlashPromotionProductRelation> implements ISmsFlashPromotionProductRelationService {

    @Autowired
    private SmsFlashPromotionProductRelationMapper flashPromotionProductRelationMapper;


    /**
     * 分页查询不同场次关联及商品信息
     * @param flashPromotionId        活动id
     * @param flashPromotionSessionId 场次id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsFlashPromotionProductRelationDetail> getFlashPromotionSession(Long flashPromotionId, Long flashPromotionSessionId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return flashPromotionProductRelationMapper.getFlashPromotionSession(flashPromotionId, flashPromotionSessionId);
    }
}
