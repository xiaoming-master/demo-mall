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
 * 会员登录记录
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UmsMemberLoginLog对象", description="会员登录记录")
public class UmsMemberLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private LocalDateTime createTime;

    private String ip;

    private String city;

    @ApiModelProperty(value = "登录类型：0->PC；1->android;2->ios;3->小程序")
    private Integer loginType;

    private String province;


}
