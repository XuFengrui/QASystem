package com.qa.system.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName WebAppConfigUtils
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/23
 * @Version 1.0
 **/
@Configuration
public class WebAppConfigUtils extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("D:/icon/**").addResourceLocations("file:D:/icon/");
    }
}
