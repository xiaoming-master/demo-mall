package com.ming.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源分类表
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmsResourceCategory对象", description="资源分类表")
public class UmsResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
