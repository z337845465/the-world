package com.jerry.theworld.pojo;

import com.jerry.theworld.redis.impl.RedisCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Author:Jerry
 * 2020/4/17
 */
public class GoodsTask implements Runnable {

    private Goods goods;
    private CountDownLatch latch;

    @Autowired
    private RedisCacheServiceImpl cacheService;


    public GoodsTask(Goods goods, CountDownLatch latch) {
        this.goods = goods;
        this.latch = latch;

    }

    private void testd(){
        try {
            long l = System.currentTimeMillis();
            if(cacheService.lock(goods.getName(),l+"",1000)){
                System.out.println("========抢到锁了=========="+Thread.currentThread().getName());
                cacheService.unlock(goods.getName(),l+"");
            }else {
                System.out.println("等待锁"+Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.testd();
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
