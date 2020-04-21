package com.jerry.theworld.interceptor;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterAop {
    //nx: key不存在设置  xx：key存在设置
    String nxxx() default "nx";
    String exxx() default "se";
    long time() default 2*60;
}
