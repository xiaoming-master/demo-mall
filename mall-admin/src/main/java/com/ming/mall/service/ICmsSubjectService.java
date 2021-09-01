package com.ming.mall.service;

import com.ming.mall.model.CmsSubject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专题表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface ICmsSubjectService extends IService<CmsSubject> {


    /**
     * 根据专题名称分页获取专题
     * @param keyword 关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<CmsSubject> getSubjectByKeyword(String keyword, Integer pageNum, Integer pageSize);
}
