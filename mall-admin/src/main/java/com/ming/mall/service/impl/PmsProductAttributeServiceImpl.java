package com.ming.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.AttrInfo;
import com.ming.mall.dto.PmsProductAttributeParam;
import com.ming.mall.mapper.PmsProductAttributeCategoryMapper;
import com.ming.mall.mapper.PmsProductAttributeMapper;
import com.ming.mall.model.PmsProductAttribute;
import com.ming.mall.model.PmsProductAttributeCategory;
import com.ming.mall.service.IPmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements IPmsProductAttributeService {

    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    /**
     * 通过商品分类id获取属性以及分类信息
     *
     * @param productCategoryId
     * @return
     */
    @Override
    public List<AttrInfo> getAttributeInfoByProductCateGoryId(Long productCategoryId) {
        return productAttributeMapper.getAttributeInfoByProductCateGoryId(productCategoryId);
    }

    /**
     * 添加商品属性
     *
     * @param productAttributeParam
     * @return
     */
    @Transactional
    @Override
    public int addProductAttribute(PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute productAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, productAttribute);
        int count = productAttributeMapper.insert(productAttribute);
        //更新属性分类的attribute_count或param_count
        //获取属性分类信息
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectById(productAttribute.getProductAttributeCategoryId());
        //productAttribute.getType()->0表示这个属性用于规格描述,1表示这个属性用于参数描述
        if (productAttribute.getType() == 0) {
            productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
        } else if (productAttribute.getType() == 1) {
            productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() + 1);
        }
        productAttributeCategoryMapper.updateById(productAttributeCategory);
        return count;
    }

    /**
     * 批量删除商品属性
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public int deleteProductAttributeByIds(List<Integer> ids) {
        //获取属性分类信息
        PmsProductAttribute productAttribute = productAttributeMapper.selectById(ids.get(0));
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectById(productAttribute.getProductAttributeCategoryId());
        //获取属性类型->0表示这个属性用于规格描述,1表示这个属性用于参数描述
        Integer type = productAttribute.getType();
        //删除
        int count = productAttributeMapper.deleteBatchIds(ids);
        //更新属性分类的attribute_count或param_count
        if (type == 0) {
            if (productAttributeCategory.getAttributeCount() > count) {
                productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() - count);
            } else {
                productAttributeCategory.setAttributeCount(0);
            }
        } else if (type == 1) {
            if (productAttributeCategory.getParamCount() > count) {
                productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() - count);
            } else {
                productAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryMapper.updateById(productAttributeCategory);

        return count;
    }


    /**
     * 根据分类属性查询分类信息
     *
     * @param cid      属性分类id
     * @param pageNum
     * @param pageSize
     * @param type     属性类型->0表示这个属性用于规格描述,1表示这个属性用于参数描述
     * @return
     */
    @Override
    public List<PmsProductAttribute> getProductAttribute(Long cid,
                                                         Integer pageNum,
                                                         Integer pageSize,
                                                         Integer type) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<PmsProductAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_attribute_category_id", cid).eq("type", type);
        return productAttributeMapper.selectList(queryWrapper);
    }
}
