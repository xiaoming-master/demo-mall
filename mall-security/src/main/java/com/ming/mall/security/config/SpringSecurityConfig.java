package com.ming.mall.security.config;

import com.ming.mall.security.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: ming
 * @create: 2021-08-18 17:39
 * @program: demo-mall
 */
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @Autowired
    private IUserDetailsService userDetailsService;

    @Autowired
    private EmailSecurityConfig emailSecurityConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //直接放行
        web.ignoring().antMatchers("/admin/login",
                "/admin/register",
                "/druid/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/swagger/**",
                "/**/v2/api-docs",
                "/**/*.js",
                "/**/*.css",
                "/**/*.png",
                "/**/*.ico",
                "/webjars/springfox-swagger-ui/**");
    }

    /**
     * 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .formLogin()
                .loginProcessingUrl("/admin/email/login");

        http.authorizeRequests()
//                .antMatchers("/admin/email/login")
//                .permitAll()
//        任何请求都需验证
                .anyRequest()
                .authenticated()
                .and().apply(emailSecurityConfig);

        // 关闭跨站请求防护
        http.csrf().disable()
                //禁用session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //添加拦截器到UsernamePasswordAuthenticationFilter.class之前
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //权限拒绝处理器
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint());

        //动态权限配置
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(dynamicSecurityMetadataSource());
                o.setAccessDecisionManager(dynamicAccessDecisionManager());
                return o;
            }
        });

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    //当dynamicSecurityService注入了，才能注入
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }


    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

}
