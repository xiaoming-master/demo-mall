package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.ProductQueryParam;
import com.ming.mall.mapper.PmsProductMapper;
import com.ming.mall.model.PmsProduct;
import com.ming.mall.service.IPmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    @Autowired
    private PmsProductMapper productMapper;

    /**
     * 模糊分页查询商品
     *
     * @param queryParam
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PmsProduct> getProduct(ProductQueryParam queryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //封装参数
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_status", 0);
        queryWrapper.eq(queryParam.getBrandId() != null, "brand_id", queryParam.getBrandId());
        queryWrapper.eq(queryParam.getProductCategoryId() != null, "product_category_id", queryParam.getProductCategoryId());
        queryWrapper.eq(StrUtil.isNotEmpty(queryParam.getProductSn()), "product_sn", queryParam.getProductSn());
        queryWrapper.eq(queryParam.getPublishStatus() != null, "publish_status", queryParam.getPublishStatus());
        queryWrapper.eq(queryParam.getVerifyStatus() != null, "verify_status", queryParam.getVerifyStatus());
        queryWrapper.like(StrUtil.isNotEmpty(queryParam.getKeyword()), "name", queryParam.getKeyword());
        //查询
        return productMapper.selectList(queryWrapper);
    }
}
