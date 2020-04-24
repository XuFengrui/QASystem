package com.qa.system.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebAppConfigUtils
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/23
 * @Version 1.0
 **/
@Configuration
public class WebAppConfigUtils implements WebMvcConfigurer {

    @Value("${uploadFile.path}")
    private String uploadFilePath;

    @Value("${uploadFile.staticAccessPath}")
    private String staticAccessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFilePath);
    }
}
