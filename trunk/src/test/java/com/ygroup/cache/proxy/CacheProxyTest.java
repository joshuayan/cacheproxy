/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy;

import com.ygroup.cache.proxy.annotation.Cached;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class CacheProxyTest
{
    @Cached(key="{para_0}_{para_1}")
    public String testCache(String name,int age)
    {
        System.out.println("testcache.done");
        return "test";
    }
    public static void main(String[] a)
    {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        CacheProxyTest test=(CacheProxyTest) context.getBean("cacheTest");
        String result1=test.testCache("name1", 12);
        String result2=test.testCache("name1", 12);
        System.out.println("done");
    }
}
