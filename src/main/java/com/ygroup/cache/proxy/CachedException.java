/**
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 */
package com.ygroup.cache.proxy;

/**
 * @author danson.liu
 *
 */
public class CachedException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 4681921364101869714L;

    /**
     * @see com.dianping.modules.core.exception.RuntimeBusinessException#RuntimeBusinessException(String)
     */
    public CachedException(String msg)
    {
        super(msg);
    }

    /**
     * @see com.dianping.modules.core.exception.RuntimeBusinessException#RuntimeBusinessException(String, Throwable)
     */
    public CachedException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    /**
     * @see com.dianping.modules.core.exception.RuntimeBusinessException#RuntimeBusinessException(Throwable)
     * @param cause
     */
    public CachedException(Throwable cause)
    {
        super(cause);
    }
}
