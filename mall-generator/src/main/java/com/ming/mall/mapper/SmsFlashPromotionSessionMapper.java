package com.ming.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.mall.dto.SmsFlashPromotionSessionDetail;
import com.ming.mall.model.SmsFlashPromotionSession;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 限时购场次表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface SmsFlashPromotionSessionMapper extends BaseMapper<SmsFlashPromotionSession> {


    /**
     * 根据活动id获取全部场次以及商品的数量
     *
     * @param flashPromotionId
     * @return
     */
    List<SmsFlashPromotionSessionDetail> getFlashPromotionSessionAndProductCount(@Param(value = "flashPromotionId") Long flashPromotionId);
}
