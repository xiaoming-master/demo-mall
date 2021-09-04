package com.ming.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.dto.SmsCouponParam;
import com.ming.mall.mapper.SmsCouponMapper;
import com.ming.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.ming.mall.mapper.SmsCouponProductRelationMapper;
import com.ming.mall.model.SmsCoupon;
import com.ming.mall.model.SmsCouponProductCategoryRelation;
import com.ming.mall.model.SmsCouponProductRelation;
import com.ming.mall.service.ISmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements ISmsCouponService {

    @Autowired
    private SmsCouponMapper couponMapper;

    @Autowired
    private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;

    @Autowired
    private SmsCouponProductRelationMapper couponProductRelationMapper;

    /**
     * 根据优惠券名称或类型分页模糊查询
     *
     * @param name
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsCoupon> getCoupon(String name, Integer type, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<SmsCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.like(StrUtil.isNotEmpty(name), "name", name);
        return couponMapper.selectList(queryWrapper);
    }

    /**
     * 添加优惠券
     *
     * @param couponParam
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public int addCoupon(SmsCouponParam couponParam) {
        //非法参数验证
        if (couponParam.getType() > 2 || couponParam.getPlatform() > 2 || couponParam.getUseType() > 2) {
            return 0;
        }
        SmsCoupon coupon = couponParam;
        //先插入，得到id
        int count = couponMapper.insert(coupon);
        Long couponId = coupon.getId();

        List<SmsCouponProductCategoryRelation> productCategoryRelationList = couponParam.getProductCategoryRelationList();
        List<SmsCouponProductRelation> productRelationList = couponParam.getProductRelationList();
        if (couponParam.getUseType().equals(1) && CollUtil.isNotEmpty(productCategoryRelationList)) {//指定分类
            //设置优惠券id
            for (SmsCouponProductCategoryRelation relation : productCategoryRelationList) {
                relation.setCouponId(couponId);
            }
            //插入到数据库
            couponProductCategoryRelationMapper.insertList(productCategoryRelationList);
        } else if (couponParam.getUseType().equals(2) && CollUtil.isNotEmpty(productRelationList)) {//指定商品
            //设置优惠券id
            for (SmsCouponProductRelation relation : productRelationList) {
                relation.setCouponId(couponId);
            }
            //批量插入
            couponProductRelationMapper.insertList(productRelationList);
        }
        return count;
    }

    /**
     * 通过优惠券id获取优惠券信息
     *
     * @param couponId
     * @return
     */
    @Override
    public SmsCouponParam getCouponById(Long couponId) {
        return couponMapper.getCouponById(couponId);
    }


    /**
     * 修改优惠券信息
     *
     * @param couponId
     * @param couponParam
     * @return
     */
    @Transactional
    @Override
    public int updateCoupon(Long couponId, SmsCouponParam couponParam) {
        couponParam.setId(couponId);
        SmsCoupon coupon = couponParam;
        int count = couponMapper.updateById(coupon);

        //删除旧的关系
        couponProductCategoryRelationMapper.delete(new QueryWrapper<SmsCouponProductCategoryRelation>().eq("coupon_id", couponId));
        couponProductRelationMapper.delete(new QueryWrapper<SmsCouponProductRelation>().eq("coupon_id", couponId));

        //插入新的关系
        List<SmsCouponProductCategoryRelation> productCategoryRelationList = couponParam.getProductCategoryRelationList();
        List<SmsCouponProductRelation> productRelationList = couponParam.getProductRelationList();
        if (couponParam.getUseType().equals(1) && CollUtil.isNotEmpty(productCategoryRelationList)) {//指定分类
            //设置优惠券id
            for (SmsCouponProductCategoryRelation relation : productCategoryRelationList) {
                relation.setCouponId(couponId);
            }
            //插入到数据库
            couponProductCategoryRelationMapper.insertList(productCategoryRelationList);
        } else if (couponParam.getUseType().equals(2) && CollUtil.isNotEmpty(productRelationList)) {//指定商品
            //设置优惠券id
            for (SmsCouponProductRelation relation : productRelationList) {
                relation.setCouponId(couponId);
            }
            //批量插入
            couponProductRelationMapper.insertList(productRelationList);
        }
        return count;
    }


}
