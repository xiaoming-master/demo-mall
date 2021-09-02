package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.mall.mapper.SmsHomeRecommendSubjectMapper;
import com.ming.mall.model.SmsHomeRecommendSubject;
import com.ming.mall.service.ISmsHomeRecommendSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class SmsHomeRecommendSubjectServiceImpl extends ServiceImpl<SmsHomeRecommendSubjectMapper, SmsHomeRecommendSubject> implements ISmsHomeRecommendSubjectService {

    @Autowired
    private SmsHomeRecommendSubjectMapper homeRecommendSubjectMapper;


    /**
     * 根据专题名称和推荐状态分页模糊查询
     *
     * @param subjectName
     * @param recommendStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SmsHomeRecommendSubject> getRecommendSubject(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize) {

        QueryWrapper<SmsHomeRecommendSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(recommendStatus != null, "recommend_status", recommendStatus);
        queryWrapper.like(StrUtil.isNotEmpty(subjectName), "subject_name", subjectName);

        return homeRecommendSubjectMapper.selectList(queryWrapper);

    }
}
