/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy.application;

/**
 * 
 * @author Joshua
 */
public interface CacheService
{
    <T> T get(String key, Class<T> claz);

    void put(String key, Object data, int expiredSeconds);

    void remove(String key);
}
