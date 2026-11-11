package com.calcmadeeasy.config;

import java.util.List;

import com.calcmadeeasy.config.resolver.CurrentUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registers the resolver with Spring MVC.
 * Without this file, Spring does not know the resolver exists.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CurrentUserResolver currentUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserResolver);
    }
}
