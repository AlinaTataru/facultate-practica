package com.example.programare_examene.common.config.security;

import com.example.programare_examene.common.util.AppProps;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final AppProps appProps;

    public MvcConfig(AppProps appProps) {
        this.appProps = appProps;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(appProps.appUrl)
                .allowCredentials(true);
    }
}
