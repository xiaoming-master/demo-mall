package com.ming.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优选专区和产品关系表
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CmsPrefrenceAreaProductRelation对象", description="优选专区和产品关系表")
public class CmsPrefrenceAreaProductRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long prefrenceAreaId;

    private Long productId;


}
