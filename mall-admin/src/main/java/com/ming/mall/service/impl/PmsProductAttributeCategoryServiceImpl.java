package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.dto.ProductAttributeCategoryWithAttr;
import com.ming.mall.mapper.PmsProductAttributeCategoryMapper;
import com.ming.mall.model.PmsProductAttributeCategory;
import com.ming.mall.service.IPmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements IPmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;


    /**
     * 分页获取商品属性分类
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PmsProductAttributeCategory> getAttributeCategory(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productAttributeCategoryMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 获取所有商品属性分类及其下属性
     * ProductAttributeCategoryWithAttr: 包装类
     * @return
     */
    @Override
    public List<ProductAttributeCategoryWithAttr> getAttributeCategoryWithAttr() {
        return productAttributeCategoryMapper.getAttributeCategoryWithAttr();
    }


}
