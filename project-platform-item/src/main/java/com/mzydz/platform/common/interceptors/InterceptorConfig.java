package com.mzydz.platform.common.interceptors;

import config.com.MyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Zhang on 2018/8/26.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public SudaPlatformInterceptor appinterceptor() {
        return new SudaPlatformInterceptor();
    }

    @Autowired
    private MyConfiguration myConfiguration;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置 不需要拦截的包和请求
        String[] excludePathPatterns = new String[]{
                "/admin/user/login",
                "/common/status/**",
                "/app/comConfig/**",
                "/file/**",
                "/page/**",
                "/index.html",
                "/static/**",
                "/error/**",
                "*.png",
                "*.json",
                "*.txt",
                "*.ico",
                "*.html",
                "*.css",
                "*.js",
                "/",
                "/exceltemplate/**",
                "/apiData/**"
        };
        registry.addInterceptor(appinterceptor()).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态 资源
        registry.addResourceHandler("/**").addResourceLocations("file:" + myConfiguration.getStaticLocations());
        registry.addResourceHandler("/exceltemplate/**").addResourceLocations("classpath:/static/exceltemplate/");
    }


}
