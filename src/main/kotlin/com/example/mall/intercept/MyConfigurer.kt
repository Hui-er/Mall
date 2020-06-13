package com.example.mall.intercept

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 *  Created by Liubin on 2019-04-04
 */
@Configuration
class MyConfigurer : WebMvcConfigurer {

    @Bean
    fun getInterceptor(): MyInterceptor {
        return MyInterceptor()
    }

    //添加拦截器
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(getInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/bin/register")
                .excludePathPatterns("/bin/login")
    }
}