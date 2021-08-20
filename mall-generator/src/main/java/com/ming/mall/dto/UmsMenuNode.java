package com.ming.mall.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ming.mall.model.UmsMenu;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 树形菜单结点
 *
 * @Author: ming
 * @create: 2021-08-20 18:17
 * @program: demo-mall
 */
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    @TableField(exist = false)
    private List<UmsMenuNode> children;

}
