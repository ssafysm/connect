package com.ssafy.cafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private ConfirmInterceptor confirmInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(confirmInterceptor)
                .addPathPatterns("/register", "/update/*", "/delete/*");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/broadcast").setViewName("forward:/broadcast.html");
    }

}
