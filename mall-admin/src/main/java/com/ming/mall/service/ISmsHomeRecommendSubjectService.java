package com.ming.mall.service;

import com.ming.mall.model.SmsHomeRecommendSubject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ISmsHomeRecommendSubjectService extends IService<SmsHomeRecommendSubject> {

    /**
     * 根据专题名称和推荐状态分页模糊查询
     * @param subjectName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SmsHomeRecommendSubject> getRecommendSubject(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize);
}
