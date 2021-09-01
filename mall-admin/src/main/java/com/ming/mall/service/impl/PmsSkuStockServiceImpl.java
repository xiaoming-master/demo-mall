package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.mall.mapper.PmsSkuStockMapper;
import com.ming.mall.model.PmsSkuStock;
import com.ming.mall.service.IPmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements IPmsSkuStockService {

    @Autowired
    private PmsSkuStockMapper skuStockMapper;

    /**
     * 根据商品id和sku编码模糊查询
     *
     * @param pid     商品id
     * @param keyword sku编码
     * @return
     */
    @Override
    public List<PmsSkuStock> getSkuStockByKeyword(Long pid, String keyword) {
        QueryWrapper<PmsSkuStock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", pid);
        queryWrapper.like(StrUtil.isNotEmpty(keyword), "sku_code", keyword);
        return skuStockMapper.selectList(queryWrapper);
    }
}
