package com.ming.mall.security.email;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ming
 * @create: 2021-09-07 13:19
 * @program: demo-mall
 */
public class EmailAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";

    public EmailAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals(HttpMethod.POST.toString())) {
            throw new AuthenticationServiceException("不支持post以外的登陆请求方式");
        }
        //获取邮箱
        String email = request.getParameter(EMAIL_PARAMETER);
        email = email != null ? email.trim() : "";
        //获取密码
        String password = request.getParameter(PASSWORD_PARAMETER);
        password = password != null ? password : "";
        //封装成一个待验证的对象
        EmailAuthenticationToken emailAuthenticationToken = new EmailAuthenticationToken(email, password);
        //保存请求的详细内容
        emailAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //验证
        return getAuthenticationManager().authenticate(emailAuthenticationToken);
    }
}
