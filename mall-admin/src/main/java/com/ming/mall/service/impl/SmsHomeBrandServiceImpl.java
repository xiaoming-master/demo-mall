package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ming.mall.model.SmsHomeBrand;
import com.ming.mall.mapper.SmsHomeBrandMapper;
import com.ming.mall.service.ISmsHomeBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsHomeBrandServiceImpl extends ServiceImpl<SmsHomeBrandMapper, SmsHomeBrand> implements ISmsHomeBrandService {

    @Autowired
    private SmsHomeBrandMapper homeBrandMapper;

    /**
     * 根据品牌名称或推荐状态模糊分页查询
     *
     * @param brandName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsHomeBrand> getRecommendBran(String brandName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<SmsHomeBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(recommendStatus != null, "recommend_status", recommendStatus);
        queryWrapper.like(StrUtil.isNotEmpty(brandName), "brand_name", brandName);
        return homeBrandMapper.selectList(queryWrapper);
    }
}
