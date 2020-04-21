package com.jerry.theworld.redis.impl;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


/**
 * Author:Jerry
 * 2020/4/15
 */
@Service
@Slf4j
public class RedisCacheServiceImpl{
//
//    /**
//     * 配置RedisTemplate，解决乱码问题
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        // string序列化方式
//        RedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
//        // 设置默认序列化方式
//        template.setDefaultSerializer(serializer);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
//        return template;
//    }

    @Autowired
    private RedisTemplate redisTemplate;

    public String getKeyForAop(JoinPoint joinpoint, HttpServletRequest request){
        Object[] args = joinpoint.getArgs();
        String key = JSON.toJSONString(args[0]);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.info("参数：{}，key：{}",JSON.toJSONString(args),key+method+uri);

        return key+method+uri+ LocalDate.now();
    }

    public String opForStrVal(String key){
       return (String) redisTemplate.opsForValue().get(key);
    }

    public Boolean opSetVal(String key, Object obj,long time){
       return redisTemplate.opsForValue().setIfAbsent(key,obj,time, TimeUnit.SECONDS);
    }

    public Boolean lock(String key,String value,long timeout){
        if(redisTemplate.opsForValue().setIfAbsent(key,value,timeout,TimeUnit.MILLISECONDS)){
            return true;
        }
//
//        String currentValue = (String) redisTemplate.opsForValue().get(key);
//        //解决死锁，且当多个线程同时来时，只会让一个线程拿到锁
//        //如果过期
//        if (!StringUtils.isEmpty(currentValue) &&
//                Long.parseLong(currentValue) < System.currentTimeMillis()){
//            //获取上一个锁的时间
//            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
//            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
//                return true;
//            }
//        }
        return false;
    }

    public void unlock(String key, String value){

        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis锁】解锁失败, {}", e);
        }
    }

}
