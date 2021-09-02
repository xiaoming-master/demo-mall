package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.mapper.SmsHomeNewProductMapper;
import com.ming.mall.model.SmsHomeNewProduct;
import com.ming.mall.service.ISmsHomeNewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsHomeNewProductServiceImpl extends ServiceImpl<SmsHomeNewProductMapper, SmsHomeNewProduct> implements ISmsHomeNewProductService {


    @Autowired
    private SmsHomeNewProductMapper homeNewProductMapper;

    /**
     * 根据推荐状态或商品名称分页模糊查询推荐新品
     *
     * @param productName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsHomeNewProduct> getCommendProduct(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<SmsHomeNewProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(recommendStatus != null, "recommend_status", recommendStatus);
        queryWrapper.like(StrUtil.isNotEmpty(productName), "product_name", productName);
        return homeNewProductMapper.selectList(queryWrapper);
    }
}
