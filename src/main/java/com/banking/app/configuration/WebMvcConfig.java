package com.banking.app.configuration;

import com.banking.app.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    HandlerInterceptor yourInjectedInterceptor;

    @Autowired
    AuthenticationInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("user");
    }
}