package com.calcmadeeasy.config.resolver;

import com.calcmadeeasy.config.annotation.CurrentUser;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Does the extraction of the user's UUID.
 * If a controller parameter has @CurrentUser, create that value this way.
 * Implements HandlerMethodArgumentResolver which is a Spring MVC interface for resolving custom controller method arguments.
 * 
 * CurrentUserResolver
 */
@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    // Necessary to tell Spring that this is the supported annotation (@CurrentUser) and to not whine.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null;
    }

    // Extracts the actual user's UUID and returns it.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(auth.getName());
    }
}
