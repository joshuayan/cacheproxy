/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy.client;

/**
 * 
 * @author Joshua
 */
public interface CacheClient
{
    <T> T get(String key, Class<T> claz);

    void set(String key, Object data, int expiredSeconds);

    void remove(String key);
}
