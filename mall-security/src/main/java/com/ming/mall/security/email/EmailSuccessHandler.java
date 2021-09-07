package com.ming.mall.security.email;

import cn.hutool.json.JSONUtil;
import com.ming.mall.common.api.CommonResult;
import com.ming.mall.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Author: ming
 * @create: 2021-09-07 14:46
 * @program: demo-mall
 */
@Component
public class EmailSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HashMap<String, Object> map = new HashMap<>();
        map.put("tokenHead", "Bearer");
        map.put("token", jwtTokenUtil.generateTokenByUsername(authentication.getName()));
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(CommonResult.success(map, "登陆成功")));
        writer.flush();
    }


}
