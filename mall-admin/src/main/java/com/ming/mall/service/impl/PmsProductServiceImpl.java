package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.PmsProductParam;
import com.ming.mall.dto.ProductQueryParam;
import com.ming.mall.mapper.*;
import com.ming.mall.model.*;
import com.ming.mall.service.IPmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;

    @Autowired
    private CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;

    @Autowired
    private PmsProductLadderMapper productLadderMapper;

    @Autowired
    private PmsSkuStockMapper skuStockMapper;

    @Autowired
    private PmsProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    private PmsProductFullReductionMapper productFullReductionMapper;

    @Autowired
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;

    @Autowired
    private PmsProductVertifyRecordMapper productVertifyRecordMapper;

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
        if (ObjectUtil.isNotEmpty(queryParam)) {
            queryWrapper.eq(queryParam.getBrandId() != null, "brand_id", queryParam.getBrandId());
            queryWrapper.eq(queryParam.getProductCategoryId() != null, "product_category_id", queryParam.getProductCategoryId());
            queryWrapper.eq(StrUtil.isNotEmpty(queryParam.getProductSn()), "product_sn", queryParam.getProductSn());
            queryWrapper.eq(queryParam.getPublishStatus() != null, "publish_status", queryParam.getPublishStatus());
            queryWrapper.eq(queryParam.getVerifyStatus() != null, "verify_status", queryParam.getVerifyStatus());
            queryWrapper.like(StrUtil.isNotEmpty(queryParam.getKeyword()), "name", queryParam.getKeyword());
        }
        //查询
        return productMapper.selectList(queryWrapper);
    }


    /**
     * 创建商品
     *
     * @param productParam
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public int addProduct(PmsProductParam productParam) {
        //父类引用指向子类对象
        PmsProduct product = productParam;
        product.setId(null);
        productMapper.insert(product);
        //插入商品表后得到返回的id
        Long productId = product.getId();

        //会员价格
        List<PmsMemberPrice> memberPriceList = productParam.getMemberPriceList();
        //批量插入
        relateAndInsertList(memberPriceMapper, memberPriceList, productId);

        //关联优选商品
        List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList = productParam.getPrefrenceAreaProductRelationList();
        //批量插入
        relateAndInsertList(prefrenceAreaProductRelationMapper, prefrenceAreaProductRelationList, productId);

        //产品阶梯价格
        List<PmsProductLadder> productLadderList = productParam.getProductLadderList();
        relateAndInsertList(productLadderMapper, productLadderList, productId);
        //sku库存
        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();
        relateAndInsertList(skuStockMapper, skuStockList, productId);
        //产品参数值
        List<PmsProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        relateAndInsertList(productAttributeValueMapper, productAttributeValueList, productId);
        //满减策略
        List<PmsProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        relateAndInsertList(productFullReductionMapper, productFullReductionList, productId);
        //专题商品
        List<CmsSubjectProductRelation> subjectProductRelationList = productParam.getSubjectProductRelationList();
        relateAndInsertList(subjectProductRelationMapper, subjectProductRelationList, productId);
        int count = 1;
        return count;
    }


    /**
     * 更新商品信息
     *
     * @param productId
     * @param productParam
     * @return
     */
    @Transactional
    @Override
    public int updateProduct(Long productId, PmsProductParam productParam) {

        PmsProduct product = productParam;
        product.setId(productId);
        //更新商品表数据
        productMapper.updateById(product);

        //会员价格
        List<PmsMemberPrice> memberPriceList = productParam.getMemberPriceList();
        //删除旧数据
        memberPriceMapper.delete(new QueryWrapper<PmsMemberPrice>().eq("product_id", productId));
        //插入要更新的数据
        relateAndInsertList(memberPriceMapper, memberPriceList, productId);

        //优选商品
        List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList = productParam.getPrefrenceAreaProductRelationList();
        //删除旧数据
        prefrenceAreaProductRelationMapper.delete(new QueryWrapper<CmsPrefrenceAreaProductRelation>().eq("product_id", productId));
        //插入新数据
        relateAndInsertList(prefrenceAreaProductRelationMapper, prefrenceAreaProductRelationList, productId);

        //产品属性值
        List<PmsProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueMapper.delete(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", productId));
        //插入新数据
        relateAndInsertList(productFullReductionMapper, productAttributeValueList, productId);

        //满减
        List<PmsProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionMapper.delete(new QueryWrapper<PmsProductFullReduction>().eq("product_id", productId));
        relateAndInsertList(productFullReductionMapper, productFullReductionList, productId);

        //阶梯价格
        List<PmsProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderMapper.delete(new QueryWrapper<PmsProductLadder>().eq("product_id", productId));
        relateAndInsertList(productLadderMapper, productLadderList, productId);

        //sku
        List<PmsSkuStock> skuStockList = productParam.getSkuStockList();
        skuStockMapper.delete(new QueryWrapper<PmsSkuStock>().eq("product_id", productId));
        relateAndInsertList(skuStockMapper, skuStockList, productId);

        //专题
        List<CmsSubjectProductRelation> subjectProductRelationList = productParam.getSubjectProductRelationList();
        subjectProductRelationMapper.delete(new QueryWrapper<CmsSubjectProductRelation>().eq("product_id", productId));
        relateAndInsertList(subjectProductRelationMapper, subjectProductRelationList, productId);

        int count = 1;
        return count;
    }


    /**
     * 批量修改审核状态
     *
     * @param detail
     * @param ids
     * @param verifyStatus
     * @param adminName
     * @return
     */
    @Transactional
    @Override
    public int updateVerifyStatus(String detail, List<Long> ids, Integer verifyStatus, String adminName) {
        //修改商品审核状态
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        int count = productMapper.update(product, queryWrapper);

        //添加审核记录
        PmsProductVertifyRecord record = new PmsProductVertifyRecord();
        record.setCreateTime(new Date());
        record.setDetail(detail);
        record.setStatus(verifyStatus);
        record.setVertifyMan(adminName);
        productVertifyRecordMapper.insertList(record, ids);
        return count;
    }


    /**
     * 根据id获取商品信息
     * @param productId
     * @return
     */
    @Override
    public PmsProduct getProductById(Long productId) {
        return productMapper.getProductById(productId);
    }


    /**
     * 建立插入关系表
     *
     * @param mapper
     * @param list
     * @param productId
     */
    private void relateAndInsertList(Object mapper, List list, Long productId) {
        try {
            //设置productId
            if (CollUtil.isNotEmpty(list)) {
                for (Object item : list) {
                    //反射获取setProductId方法
                    Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                    setProductId.invoke(item, productId);
                }
                //执行mapper的insertList方法
                Method insertList = mapper.getClass().getMethod("insertList", List.class);
                insertList.invoke(mapper, list);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOGGER.warn("添加商品出现错误，原因：{}", e.getCause().toString());
        }
    }

}
