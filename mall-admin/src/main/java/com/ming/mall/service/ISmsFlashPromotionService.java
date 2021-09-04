package com.ming.mall.service;

import com.ming.mall.model.SmsFlashPromotion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 限时购表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsFlashPromotionService extends IService<SmsFlashPromotion> {

    /**
     * 通过活动名称模糊分页查询活动信息
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsFlashPromotion> getFlashPromotion(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 创建限时活动
     * @param flashPromotion
     * @return
     */
    int addFlashPromotion(SmsFlashPromotion flashPromotion);
}
