package com.game.common;

import org.springframework.context.ApplicationContext;

/**
 * 〈一句话功能简述〉<br>
 * 〈Spring上下文引用对象〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public class SpringContext {

    /** Spring上下文 */
    private static ApplicationContext applicationContext = null;

    /**
     * 缓存Spring容器对象
     *
     * @param applicationContext
     */
    public static void cacheSpringApplication(ApplicationContext applicationContext){
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取bean对象
     *
     * @param clz
     * @return
     */
    public static Object getBean(Class<?> clz){
        return applicationContext.getBean(clz);
    }
}