package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.mapper.SmsFlashPromotionMapper;
import com.ming.mall.model.SmsFlashPromotion;
import com.ming.mall.service.ISmsFlashPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 限时购表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsFlashPromotionServiceImpl extends ServiceImpl<SmsFlashPromotionMapper, SmsFlashPromotion> implements ISmsFlashPromotionService {

    @Autowired
    private SmsFlashPromotionMapper flashPromotionMapper;


    /**
     * 通过活动名称模糊分页查询活动信息
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsFlashPromotion> getFlashPromotion(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return flashPromotionMapper.selectList(new QueryWrapper<SmsFlashPromotion>().like(StrUtil.isNotEmpty(keyword), "title", keyword));
    }


    /**
     * 创建限时活动
     *
     * @param flashPromotion
     * @return
     */
    @Override
    public int addFlashPromotion(SmsFlashPromotion flashPromotion) {
        flashPromotion.setCreateTime(new Date());
        return flashPromotionMapper.insert(flashPromotion);
    }
}
