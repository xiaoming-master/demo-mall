package com.ming.mall.service;

import com.ming.mall.model.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface IPmsBrandService extends IService<PmsBrand> {

    /**
     * 添加品牌
     * @param pmsBrand
     * @return
     */
    int addBrand(PmsBrand pmsBrand);


    /**
     * 根据品牌名称分页模糊查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsBrand> getBrandByKeyword(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 更新品牌信息
     * @param id
     * @param pmsBrand
     * @return
     */
    int updateBrand(Long id, PmsBrand pmsBrand);
}
