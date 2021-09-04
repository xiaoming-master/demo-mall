package com.ming.mall.service;

import com.ming.mall.dto.SmsFlashPromotionSessionDetail;
import com.ming.mall.model.SmsFlashPromotionSession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 限时购场次表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsFlashPromotionSessionService extends IService<SmsFlashPromotionSession> {


    /**
     * 根据活动id获取全部场次以及商品的数量
     * @param flashPromotionId
     * @return
     */
    List<SmsFlashPromotionSessionDetail> getFlashPromotionSessionAndProductCount(Long flashPromotionId);
}
