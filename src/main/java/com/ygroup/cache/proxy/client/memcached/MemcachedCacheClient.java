/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy.client.memcached;

import com.ygroup.cache.proxy.client.CacheClient;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Administrator
 */
public class MemcachedCacheClient implements CacheClient
{
    private static Logger logger = LoggerFactory.getLogger(MemcachedCacheClient.class);
    private MemcachedClient memcachedClient;

    public void setMemcachedClient(MemcachedClient memcachedClient)
    {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public <T> T get(String key, Class<T> claz)
    {
        logger.debug("get object [{}]", key);
        T result = null;
        try
        {
            Object obj = this.memcachedClient.get(key);
            if (null != obj)
            {
                result = (T) obj;
            }
        } catch (TimeoutException ex)
        {
        } catch (InterruptedException ex)
        {
        } catch (MemcachedException ex)
        {
        }

        return result;
    }

    @Override
    public void set(String key, Object data, int expiredSeconds)
    {
        try
        {
            logger.debug("set object [{}],data [{}]", key, data);
            this.memcachedClient.set(key, expiredSeconds, data);
        } catch (TimeoutException ex)
        {
        } catch (InterruptedException ex)
        {
        } catch (MemcachedException ex)
        {
        }
    }

    @Override
    public void remove(String key)
    {
        try
        {
            logger.debug("remove object [{}]", key);
            this.memcachedClient.delete(key);
        } catch (TimeoutException ex)
        {
        } catch (InterruptedException ex)
        {
        } catch (MemcachedException ex)
        {
        }
    }
}
