package com.ming.mall.security.config;

import com.ming.mall.security.component.DynamicAccessDecisionManager;
import com.ming.mall.security.component.DynamicSecurityMetadataSource;
import com.ming.mall.security.component.DynamicSecurityService;
import com.ming.mall.security.component.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()//开启跨域
                .and()
                .csrf().disable()
                .authorizeRequests()
                //不需要验证的
                //允许跨域options
                .antMatchers(HttpMethod.OPTIONS,"/admin/login", "/swagger-ui.html", "/swagger-resources/**", "/swagger/**", "/**/v2/api-docs", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.ico", "/webjars/springfox-swagger-ui/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //添加拦截器到UsernamePasswordAuthenticationFilter.class之前
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        //动态权限配置
        if (dynamicSecurityService != null) {
            http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    o.setSecurityMetadataSource(dynamicSecurityMetadataSource());
                    o.setAccessDecisionManager(dynamicAccessDecisionManager());
                    return o;
                }
            });
        }


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

}
