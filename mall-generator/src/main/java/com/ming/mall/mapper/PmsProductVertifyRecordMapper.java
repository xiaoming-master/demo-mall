package com.ming.mall.mapper;

import com.ming.mall.model.PmsProductVertifyRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品审核记录 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
public interface PmsProductVertifyRecordMapper extends BaseMapper<PmsProductVertifyRecord> {

    /**
     * 批量修改审核状态
     * @param record
     * @param ids
     * @return
     */
    int insertList(@Param(value = "record") PmsProductVertifyRecord record,@Param(value = "ids") List<Long> ids);
}
