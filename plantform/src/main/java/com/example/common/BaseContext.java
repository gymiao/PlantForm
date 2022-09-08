package com.example.common;
/*
用ThreadLocal保存获取用户Id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
