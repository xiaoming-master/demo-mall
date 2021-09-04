package com.ming.mall.service;

import com.ming.mall.dto.SmsFlashPromotionProductRelationDetail;
import com.ming.mall.model.SmsFlashPromotionProductRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品限时购与商品关系表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsFlashPromotionProductRelationService extends IService<SmsFlashPromotionProductRelation> {

    /**
     * 分页查询不同场次关联及商品信息
     * @param flashPromotionId 活动id
     * @param flashPromotionSessionId 场次id
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsFlashPromotionProductRelationDetail> getFlashPromotionSession(Long flashPromotionId, Long flashPromotionSessionId, Integer pageNum, Integer pageSize);
}
