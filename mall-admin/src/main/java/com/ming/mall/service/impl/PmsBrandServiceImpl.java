package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ming.mall.mapper.PmsBrandMapper;
import com.ming.mall.mapper.PmsProductMapper;
import com.ming.mall.model.PmsBrand;
import com.ming.mall.model.PmsProduct;
import com.ming.mall.service.IPmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements IPmsBrandService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Autowired
    private PmsProductMapper productMapper;


    /**
     * 添加品牌
     *
     * @param pmsBrand
     * @return
     */
    @Override
    public int addBrand(PmsBrand pmsBrand) {

        //如果创建时首字母为空，取名称第一个字母为首字母
        if (StrUtil.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        return brandMapper.insert(pmsBrand);
    }

    /**
     * 根据品牌名称分页模糊查询
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<PmsBrand> getBrandByKeyword(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<PmsBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(keyword), "name", keyword);
        return brandMapper.selectList(queryWrapper);
    }

    /**
     * 更新品牌信息
     *
     * @param id
     * @param pmsBrand
     * @return
     */
    @Transactional
    @Override
    public int updateBrand(Long id, PmsBrand pmsBrand) {
        //如果创建时首字母为空，取名称第一个字母为首字母
        if (StrUtil.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        //设置id
        pmsBrand.setId(id);
        //修改该品牌的商品信息
        PmsProduct product = new PmsProduct();
        product.setBrandName(pmsBrand.getName());
        productMapper.update(product, new QueryWrapper<PmsProduct>().eq("brand_id", id));
        //修改品牌信息
        return brandMapper.updateById(pmsBrand);
    }


}
