package com.example.trendtyschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigCors implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:3000")  // domain
                .allowedMethods("")
                .allowedHeaders("*");
    }
}