/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ygroup.cache.proxy.interceptor;

import com.ygroup.cache.proxy.CacheOperationType;
import java.lang.reflect.Method;
import ognl.OgnlException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.ygroup.cache.proxy.annotation.Cached;
import com.ygroup.cache.proxy.client.CacheClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import ognl.Ognl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Joshua
 */
@Aspect
@Component
public class CacheInterceptor
{
    private static Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);
    @Resource
    private CacheClient cacheClient;

    public void setCacheClient(CacheClient cacheClient)
    {
        this.cacheClient = cacheClient;
    }

    @Around("@annotation(com.ygroup.cache.proxy.annotation.Cached)")
    public Object processCacheData(ProceedingJoinPoint pjp) throws Throwable
    {
        Object result = null;
        Object[] args = pjp.getArgs();
        Map<String, Object> context = new HashMap<String, Object>(args.length);
        Class[] parametersClass = new Class[args.length];
        int i = 0;
        for (Object obj : args)
        {
            logger.debug("arg:" + obj.getClass().getName());
            context.put("para_" + i, obj);
            parametersClass[i] = obj.getClass();
            i++;
        }

        String methodName = pjp.getSignature().getName();

        Method method = findMethod(pjp.getTarget().getClass(), methodName, parametersClass);
//        Method method=ReflectionUtils.findMethod(pjp.getTarget().getClass(), methodName, parametersClass);

        Cached cacheAnn = method.getAnnotation(Cached.class);
        String cacheKey = cacheAnn.key();
        String parsedKey = parseKey(cacheKey, context);
        CacheOperationType operationType = cacheAnn.operationType();
        if (CacheOperationType.GET == operationType)
        {
            result = this.cacheClient.get(parsedKey, method.getReturnType());
            logger.debug("got value [{}] with key [{}] from cache.", result, parsedKey);
            if (null == result)
            {
                result = pjp.proceed();
                logger.debug("set value [{}] with key [{}] into cache.", result, parsedKey);
                this.cacheClient.set(parsedKey, result, cacheAnn.expiredSeconds());
            }
        }
        if (CacheOperationType.REMOVE == operationType)
        {
            logger.debug("remove key[{}] from cache", parsedKey);
            this.cacheClient.remove(parsedKey);
        }

        if (CacheOperationType.UPDATE == operationType)
        {
            logger.debug("update key[{}] into cache", parsedKey);
            this.cacheClient.remove(parsedKey);
            result = pjp.proceed();
            this.cacheClient.set(parsedKey, result, cacheAnn.expiredSeconds());
        }
        return result;
    }

    private Method findMethod(Class claz, String methodName, Class[] parameterClasses)
    {
        Method result = null;
        Method[] methods = claz.getDeclaredMethods();
        for (Method method : methods)
        {
            if (method.getName().equals(methodName) && method.getParameterTypes().length == parameterClasses.length)
            {
                result = method;
            }
        }
        return result;
    }

    private String parseKey(String keyStr, Map<String, Object> context)
    {
        String result = keyStr;
        List<String> expressions = allExpressions(keyStr);
        for (String expression : expressions)
        {
            try
            {
                Object value = Ognl.getValue(expression, context);
                if (value != null)
                {
                    result = result.replace("{" + expression + "}", value.toString());
                }
            } catch (OgnlException ex)
            {
                logger.warn("can not parse cache key.");
                result = keyStr;
                break;
            }
        }
        logger.debug("keystr:" + keyStr + ",parsed:" + result);
        return result;
    }

    private List<String> allExpressions(String para)
    {
        List<String> result = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\{([^\\{\\}]*)\\}");
        Matcher matcher = pattern.matcher(para);
        while (matcher.find())
        {
//            int count=matcher.groupCount();
            result.add(matcher.group(1));
//            System.out.println("count:"+count+",group:"+matcher.group(1));
        }
        return result;
    }

    public static void main(String[] a)
    {
        String str = "{[0].name}_{[1].age}xdw";
        Pattern pattern = Pattern.compile("\\{([^\\{\\}]*)\\}");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find())
        {
            int count = matcher.groupCount();
            System.out.println("count:" + count + ",group:" + matcher.group(1));
        }
//        CharSequence inputStr = "abbabcd";
//        String patternStr = "(a(b*))+(c*)";

// Compile and use regular expression
//        Pattern pattern = Pattern.compile(patternStr);
//        Matcher matcher = pattern.matcher(inputStr);
//        boolean matchFound = matcher.find();
//
//        if (matchFound)
//        {
//            // Get all groups for this match
//            for (int i = 0; i <= matcher.groupCount(); i++)
//            {
//                String groupStr = matcher.group(i);
//                System.out.println("groupstr:"+groupStr);
//            }
//        }

    }
}
