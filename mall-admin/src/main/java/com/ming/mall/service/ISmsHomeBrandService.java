package com.ming.mall.service;

import com.ming.mall.model.SmsHomeBrand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsHomeBrandService extends IService<SmsHomeBrand> {


    /**
     * 根据品牌名称或推荐状态模糊分页查询
     * @param brandName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsHomeBrand> getRecommendBran(String brandName, Integer recommendStatus, Integer pageNum, Integer pageSize);
}
