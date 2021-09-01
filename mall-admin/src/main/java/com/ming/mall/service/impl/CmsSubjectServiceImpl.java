package com.ming.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ming.mall.model.CmsSubject;
import com.ming.mall.mapper.CmsSubjectMapper;
import com.ming.mall.service.ICmsSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 专题表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Service
public class CmsSubjectServiceImpl extends ServiceImpl<CmsSubjectMapper, CmsSubject> implements ICmsSubjectService {

    @Autowired
    private CmsSubjectMapper subjectMapper;

    /**
     * 根据专题名称分页获取专题
     * @param keyword 关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<CmsSubject> getSubjectByKeyword(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<CmsSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(keyword), "title", keyword);
        return subjectMapper.selectList(queryWrapper);
    }
}
