package com.bujamarket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //application.properties 에 설정한 'uploadPath' 값을 읽어온다
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
           .addResourceHandler("/images/**") //url이 /images로 시작하면 uploadPath에 설정한 폴더를 기준으로 파일을 읽어온다
           .addResourceLocations(uploadPath);           //로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정한다
    }
}
