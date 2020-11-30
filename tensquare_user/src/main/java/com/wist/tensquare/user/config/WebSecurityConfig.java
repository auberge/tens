package com.wist.tensquare.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类
 *
 * @author simptx
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
          authorizeRequests 所有 Security 全注解配置实现的开端，表示开始说明需要的权限
           需要的权限分两部分，1。是拦截的路径，2.访问给路径需要的权限
          antMatchers 表示拦截什么路径，permitAll 表示任何权限都可以通行
          anyRequest 任何的请求。authenticated 认证后才能访问
          and().csrf().disable() 固定写法，表示使csrf拦截失效
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        //前后端分离要注入一个PasswordEncoder来给successHandler使用
        return new BCryptPasswordEncoder();
    }

}
