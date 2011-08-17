/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy.application.impl;

import org.apache.log4j.Logger;

import com.ygroup.cache.proxy.application.CacheService;
import com.ygroup.cache.proxy.client.CacheClient;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Joshua
 */
@Service("cacheService")
public class DefaultCacheService implements CacheService
{
    private static Logger logger = Logger.getLogger(DefaultCacheService.class);
    @Resource
    private CacheClient cacheClient;

    public void setCacheClient(CacheClient cacheClient)
    {
        this.cacheClient = cacheClient;
    }

    @Override
    public <T> T get(String key, Class<T> claz)
    {
        return this.cacheClient.get(key, claz);
    }

    @Override
    public void put(String key, Object data, int expiredSeconds)
    {
        this.cacheClient.set(key, data, expiredSeconds);
    }

    @Override
    public void remove(String key)
    {
        this.cacheClient.remove(key);
    }
}
