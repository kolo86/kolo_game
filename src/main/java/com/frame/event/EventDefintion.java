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
public class EventDefintion implements Comparable<EventDefintion>{
    /** 当前所在类 */
    private final Object bean;
    /** 事件所在方法 */
    private final Method method;
    /** 事件的字节码文件 */
    private final Class<?> clz;
    /** 当前位置被执行时的优先级。数值越小，优先级越大 */
    private int level;

    private EventDefintion(Object bean, Method method, Class<?> clz, int level){
        this.bean = bean;
        this.method = method;
        this.clz = clz;
        this.level = level;
    }

    /**
     * 构建一个事件中介对象
     *
     * @param level 具备优先级
     */
    public static EventDefintion valueOf(Object bean, Method method, int level){
        Class<?>[] parameterTypes = method.getParameterTypes();
        return new EventDefintion(bean, method, parameterTypes[0], level);
    }

    /**
     * 执行监听了该事件的方法
     *
     */
    public void invoke(IEvent event){
        ReflectionUtils.makeAccessible(this.method);
        ReflectionUtils.invokeMethod(this.method, this.bean, event);
    }

    /**
     * 排序条件
     *
     */
    @Override
    public int compareTo(EventDefintion other) {
        return this.level - other.level;
    }
}