package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.PmsProductCategoryNode;
import com.ming.mall.dto.PmsProductCategoryParam;
import com.ming.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.ming.mall.mapper.PmsProductCategoryMapper;
import com.ming.mall.mapper.PmsProductMapper;
import com.ming.mall.model.PmsProduct;
import com.ming.mall.model.PmsProductCategory;
import com.ming.mall.model.PmsProductCategoryAttributeRelation;
import com.ming.mall.service.IPmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements IPmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;

    @Autowired
    private PmsProductMapper productMapper;


    /**
     * 添加商品分类信息
     *
     * @param productCategoryParam
     * @return
     */
    @Transactional
    @Override
    public int addProductCategory(PmsProductCategoryParam productCategoryParam) {
        PmsProductCategory productCategory = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        //设置分类等级
        setCategoryLevel(productCategory);
        int count = productCategoryMapper.insert(productCategory);
        //获取商品属性
        List<Long> attributeIdList = productCategoryParam.getProductAttributeIdList();
        //批量插入分类与属性关系表
        if (CollUtil.isNotEmpty(attributeIdList)) {
            productCategoryAttributeRelationMapper.addAllRelations(productCategory.getId(), attributeIdList);
        }
        return count;
    }


    /**
     * 通过父id获取分类信息
     * @param parentId 父id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PmsProductCategory> getProductCategoryByParentId(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<PmsProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return productCategoryMapper.selectList(queryWrapper);
    }


    /**
     * 获取商品分类及其子分类
     * @return
     */
    @Override
    public List<PmsProductCategoryNode> getProductCategoryWithChildren() {
        return productCategoryMapper.getProductCategoryWithChildren(0L);
    }


    /**
     * 更新商品分类信息
     *
     * @param id
     * @param productCategoryParam
     * @return
     */
    @Transactional
    @Override
    public int updateProductCategory(Long id, PmsProductCategoryParam productCategoryParam) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategoryParam.setId(id);
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        //设置等级
        setCategoryLevel(productCategory);
        //更新商品中的商品分类名称
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(productCategory.getName());
        productMapper.update(product, new QueryWrapper<PmsProduct>().eq("product_category_id", productCategory.getId()));
        //更新属性
        List<Long> attributeIdList = productCategoryParam.getProductAttributeIdList();

        //删除关系表的记录
        productCategoryAttributeRelationMapper.delete(new QueryWrapper<PmsProductCategoryAttributeRelation>().eq("product_category_id", productCategory.getId()));
        if (CollUtil.isNotEmpty(attributeIdList)) {//属性列表不为空，再插入关系表
            //插入
            productCategoryAttributeRelationMapper.addAllRelations(productCategory.getId(), attributeIdList);
        }
        //最后更新分类信息
        return productCategoryMapper.updateById(productCategory);
    }


    /**
     * 设置分类等级
     * @param productCategory
     */
    private void setCategoryLevel(PmsProductCategory productCategory) {
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            //获取父级的信息
            PmsProductCategory parentInfo = productCategoryMapper.selectById(productCategory.getParentId());
            //通过父级等级设置等级
            if (ObjectUtil.isNotEmpty(parentInfo)) {
                productCategory.setLevel(parentInfo.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }


}
