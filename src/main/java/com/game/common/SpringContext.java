package com.game.common;

import com.frame.event.EventDispatcher;
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
     * 当Spring启动完成后，需要处理的事情
     *
     */
    public static void afterSpringStart(ApplicationContext applicationContext){
        // 缓存Spring容器对象
        SpringContext.applicationContext = applicationContext;

        // 对所有监听了事件的地方，做优先级排序
        EventDispatcher.sort();
    }

    /**
     * 获取bean对象
     *
     */
    public static Object getBean(Class<?> clz){
        return applicationContext.getBean(clz);
    }
}