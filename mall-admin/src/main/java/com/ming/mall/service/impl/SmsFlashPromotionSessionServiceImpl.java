package com.ming.mall.service.impl;

import com.ming.mall.dto.SmsFlashPromotionSessionDetail;
import com.ming.mall.model.SmsFlashPromotionSession;
import com.ming.mall.mapper.SmsFlashPromotionSessionMapper;
import com.ming.mall.service.ISmsFlashPromotionSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 限时购场次表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsFlashPromotionSessionServiceImpl extends ServiceImpl<SmsFlashPromotionSessionMapper, SmsFlashPromotionSession> implements ISmsFlashPromotionSessionService {

    @Autowired
    private SmsFlashPromotionSessionMapper flashPromotionSessionMapper;


    /**
     * 根据活动id获取全部场次以及商品的数量
     *
     * @param flashPromotionId
     * @return
     */
    @Override
    public List<SmsFlashPromotionSessionDetail> getFlashPromotionSessionAndProductCount(Long flashPromotionId) {
        return flashPromotionSessionMapper.getFlashPromotionSessionAndProductCount(flashPromotionId);
    }
}
