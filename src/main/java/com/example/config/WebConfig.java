package com.example.config;

import com.example.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于配置Spring MVC的拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 自动注入登录拦截器
    @Autowired
    private LoginInterceptor loginInterceptor;

    
     // registry 拦截器注册器，用于添加和配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 添加登录拦截器，对除登录和注册接口外的请求进行拦截
        registry.addInterceptor(loginInterceptor) // 添加拦截器
                // 放行登录接口和注册接口
                .excludePathPatterns("/user/login","/user/register");
    }
}

