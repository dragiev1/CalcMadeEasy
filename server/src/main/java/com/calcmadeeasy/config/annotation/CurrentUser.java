package com.calcmadeeasy.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Simply a custom marker annotation to use custom logic to fill in.
 * Helps in extracting user identifer UUID anywhere when user is logged in.
*/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {}