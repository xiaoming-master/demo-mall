package com.ming.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 专题评论表
 * </p>
 *
 * @author ming
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CmsSubjectComment对象", description="专题评论表")
public class CmsSubjectComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long subjectId;

    private String memberNickName;

    private String memberIcon;

    private String content;

    private LocalDateTime createTime;

    private Integer showStatus;


}
