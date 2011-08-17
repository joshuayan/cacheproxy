/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy;

import com.ygroup.cache.proxy.*;
import com.ygroup.cache.proxy.annotation.Cached;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class CacheProxyTest
{
    @Cached(key="{[0]}_{[1]}")
    public void testCache(String name,int age)
    {
        System.out.println("testcache.done");
    }
}
