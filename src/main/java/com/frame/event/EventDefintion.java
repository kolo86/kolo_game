package com.frame.event;

import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br> 
 * 〈执行事件时，需要使用的反射中介对象〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
public class EventDefintion {
    /** 当前所在类 */
    private final Object bean;
    /** 事件所在方法 */
    private final Method method;
    /** 事件的字节码文件 */
    private final Class<?> clz;

    private EventDefintion(Object bean, Method method, Class<?> clz){
        this.bean = bean;
        this.method = method;
        this.clz = clz;
    }

    /**
     * 构建一个事件中介对象
     *
     */
    public static EventDefintion valueOf(Object bean, Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        return new EventDefintion(bean, method, parameterTypes[0]);
    }

    /**
     * 执行监听了该事件的方法
     *
     */
    public void invoke(IEvent event){
        ReflectionUtils.makeAccessible(this.method);
        ReflectionUtils.invokeMethod(this.method, this.bean, event);
    }

}