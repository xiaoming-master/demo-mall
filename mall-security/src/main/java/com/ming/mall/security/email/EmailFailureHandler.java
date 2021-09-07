package com.ming.mall.security.email;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Author: ming
 * @create: 2021-09-07 14:58
 * @program: demo-mall
 */
@Component
public class EmailFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 401);
        map.put("message", exception.getMessage());
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtils.toJSONString(map));
        writer.flush();
    }
}
