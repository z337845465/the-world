package com.jerry.theworld.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jerry.theworld.controller.respone.ResponseResult;
import com.jerry.theworld.exception.GolbleHandelException;
import com.jerry.theworld.interceptor.InterAop;
import com.jerry.theworld.redis.impl.RedisCacheServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:Jerry
 * 2020/4/16
 */
@Component
@Aspect
public class CacheAscept {

    @Autowired
   private RedisCacheServiceImpl cacheService;


    @Pointcut("@annotation(com.jerry.theworld.interceptor.InterAop)")
    public void annotationAscept(){}

    @Around(value = "annotationAscept()")
    public Object doAround(ProceedingJoinPoint joinPoint){
        try {
            //获取请求
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            //接口返回值
            Object obj;
            InterAop interAop = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(InterAop.class);
            long time = interAop.time();
            String keyForAop = cacheService.getKeyForAop(joinPoint, request);
            String val = cacheService.opForStrVal(keyForAop);
            if(StringUtils.isEmpty(val)){
                obj = joinPoint.proceed();
                String jsonString = JSON.toJSONString(obj);
                Boolean isSuccess = cacheService.opSetVal(keyForAop, jsonString, time);
                if(isSuccess){
                    return obj;
                }else {
                    throw new GolbleHandelException("请求错误",501);
                }
            }
            JSONObject jsonObj = JSON.parseObject(val);
            return ResponseResult.success(jsonObj.get("responseData"));
        } catch (Throwable throwable) {
            throwable.getMessage();
        }
        return null;
    }
}
