/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ygroup.cache.proxy.CacheOperationType;

/**
 *
 * @author Joshua
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached
{
    String key();

    CacheOperationType operationType() default CacheOperationType.GET;
    
    int expiredSeconds() default 30*60;
}
