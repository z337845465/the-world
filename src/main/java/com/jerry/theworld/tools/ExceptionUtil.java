package com.jerry.theworld.tools;

/**
 * Author:Jerry
 * 2020/4/17
 */
public class ExceptionUtil {
    public static String printExceptionInfo(Exception e){
        String sOut = e.getClass().getName() + "：" + e.getMessage() + "\r\n";
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
    }
}
