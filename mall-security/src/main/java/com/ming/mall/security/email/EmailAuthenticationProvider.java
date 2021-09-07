package com.ming.mall.security.email;

import com.ming.mall.security.component.IUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: ming
 * @create: 2021-09-07 13:49
 * @program: demo-mall
 */

public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final IUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public EmailAuthenticationProvider(IUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //获取邮箱和密码
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //加载数据库中的用户信息
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);

        //密码校验
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            //构建登陆成功后的对象
            EmailAuthenticationToken authenticationToken = new EmailAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
            authenticationToken.setDetails(authentication.getDetails());
            return authenticationToken;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
