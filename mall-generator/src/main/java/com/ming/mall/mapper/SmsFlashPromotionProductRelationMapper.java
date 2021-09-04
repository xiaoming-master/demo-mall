package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.dto.SmsFlashPromotionProductRelationDetail;
import com.ming.mall.model.SmsFlashPromotionProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品限时购与商品关系表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface SmsFlashPromotionProductRelationMapper extends BaseMapper<SmsFlashPromotionProductRelation> {

    /**
     * 获取商品总数
     *
     * @param promotionId        活动id
     * @param promotionSessionId 场次id
     * @return
     */
    long getProductCount(@Param(value = "promotionId") Long promotionId, @Param(value = "promotionSessionId") Long promotionSessionId);


    /**
     * 分页查询不同场次关联及商品信息
     *
     * @param flashPromotionId        活动id
     * @param flashPromotionSessionId 场次id
     * @return
     */
    List<SmsFlashPromotionProductRelationDetail> getFlashPromotionSession(@Param(value = "flashPromotionId") Long flashPromotionId, @Param(value = "flashPromotionSessionId") Long flashPromotionSessionId);
}
