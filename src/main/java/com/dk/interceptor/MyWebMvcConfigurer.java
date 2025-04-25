package com.dk.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AuthorityHelper authorityHelper;

    private List<String> excludePathPatterns = new ArrayList<String>(){{
        add("/dk/common/image/upload");
        add("/dk/web/member/register");
        add("/dk/web/member/register/phoneCode");
        add("/dk/web/member/login");
        add("/dk/web/member/login/phoneCode");
        add("/dk/admin/user/login");
        add("/dk/admin/member/list");
        add("/dk/config/channel/check");
        add("/dk/config/spread/appPath");
    }};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityInterceptor(authorityHelper))
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(36000);
//    }
}