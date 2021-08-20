package com.ming.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登陆接口参数封装
 *
 * @Author: ming
 * @create: 2021-08-18 19:41
 * @program: demo-mall
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UmsAdminLoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
