package com.wist.tensquare.qa.config;

import com.wist.tensquare.qa.intercptor.JwtIntercptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Resource
    private JwtIntercptor jwtIntercptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器要声明拦截器对象和要拦截的请求
        registry
                .addInterceptor(jwtIntercptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
