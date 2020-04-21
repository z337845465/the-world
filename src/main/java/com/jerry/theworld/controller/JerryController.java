package com.jerry.theworld.controller;

import com.jerry.theworld.controller.respone.ResponseResult;
import com.jerry.theworld.interceptor.InterAop;
import com.jerry.theworld.redis.impl.RedisCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:Jerry
 * 2020/4/15
 */
@RestController
@RequestMapping("/Jerry")
public class JerryController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisCacheServiceImpl cacheService;

    @RequestMapping(value = "testGet/{id}",method = RequestMethod.GET)
    public ResponseResult testGet(@PathVariable String id){
        Object o = redisTemplate.opsForValue().get(id);
        if(o==null){

            redisTemplate.opsForValue().set(id,id,52, TimeUnit.SECONDS);
            return ResponseResult.success();
        }
        return ResponseResult.result(200,"错误",null);
    }

    @RequestMapping(value = "testAop/{name}/{birthday}",method = RequestMethod.GET)
//    @InterAop(time = 60)
    public ResponseResult testAop(@PathVariable String name,@PathVariable String birthday) throws InterruptedException {

        String name1 = Thread.currentThread().getName();
        Boolean lock = cacheService.lock(name, "2400", 1200);
        if(!lock){
            return ResponseResult.fail("抢不到锁"+name1);
        }
        System.out.println(name1+"===========抢到锁了=====");
//        Thread.sleep(500);
        cacheService.unlock(name,"2400");
        String result=name+"的生日是:"+birthday;

        return ResponseResult.result(200,name1,result);
    }

    public static void main(String[] args) {
        int a = 2;
        int b = (a++) << (++a) + (++a);
        System.out.println(b);
        int c=2,d;
        d=c++ + ++c;
        System.out.println(d);
        System.out.println(2l<<61);

    }
}
