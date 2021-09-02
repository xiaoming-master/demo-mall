package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ming.mall.model.SmsHomeRecommendProduct;
import com.ming.mall.mapper.SmsHomeRecommendProductMapper;
import com.ming.mall.service.ISmsHomeRecommendProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsHomeRecommendProductServiceImpl extends ServiceImpl<SmsHomeRecommendProductMapper, SmsHomeRecommendProduct> implements ISmsHomeRecommendProductService {

    @Autowired
    private SmsHomeRecommendProductMapper homeRecommendProductMapper;

    /**
     * 根据商品名称或推荐状态模糊分页查询
     *
     * @param productName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsHomeRecommendProduct> getRecommendProduct(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<SmsHomeRecommendProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(recommendStatus != null, "recommend_status", recommendStatus);
        queryWrapper.like(StrUtil.isNotEmpty(productName), "product_name", productName);
        return homeRecommendProductMapper.selectList(queryWrapper);
    }

}
