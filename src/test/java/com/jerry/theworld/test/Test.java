package com.jerry.theworld.test;

import com.jerry.theworld.pojo.Goods;
import com.jerry.theworld.pojo.GoodsTask;
import com.jerry.theworld.redis.impl.RedisCacheServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Author:Jerry
 * 2020/4/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Test {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisCacheServiceImpl cacheService;

    @org.junit.Test
    public void test1(){
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("jerry2", "hei23", 12, TimeUnit.SECONDS);
            System.out.println(aBoolean);
            Boolean aBoolean1 = redisTemplate.opsForValue().setIfAbsent("jerry2", "hei24", 12, TimeUnit.SECONDS);
            String jerry2 = (String) redisTemplate.opsForValue().get("jerry2");
            System.out.println(jerry2);
            System.out.println(aBoolean1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @org.junit.Test
    public void test2(){
        try {
            ExecutorService executor = Executors.newFixedThreadPool(52);
            List<CompletableFuture> futureList=new ArrayList<>(32);
            for (int i = 0; i < 512; i++) {
//				CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> http()).thenApplyAsync((list)->doIt(list));
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->http(Thread.currentThread().getName()),executor);
                futureList.add(future);

            }
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
            allOf.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  String http(String name) {
        try {

            //TODO: 准备uri
            URI uri = new URI("http://localhost:8024/Jerry/testAop/TomAndJerry/08-24");
//			URI uri = new URI("http://localhost:7000/demo/getUserInfoAnnotationWithException.json");
//			URI uri = new URI("http://localhost:7000/demo/getUserInfoAnnotationWithString.json");
//			URI uri = new URI("http://localhost:8052/login");

            //TODO: new一个HTTP工厂
            SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();

            //TODO: 创建HTTP请求
            ClientHttpRequest chr = schr.createRequest(uri, HttpMethod.GET);

//			Map param=new HashMap();
//			param.put("userName","Jerry");
//			param.put("password","123456");


            //TODO：设置body
//			chr.getBody().write(param.getBytes());
            //TODO: 执行HTTP请求
            ClientHttpResponse res = chr.execute();

            //TODO: 获取返回数据流
            InputStream is = res.getBody();

            //TODO: 从流中读取数据
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str = "";

            while ((str = br.readLine()) != null) {
				System.out.println(str);
                return str;
            }

        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return "ABC";
    }

}
