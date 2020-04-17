package com.frame.dispatcher;

import io.netty.channel.Channel;
import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br> 
 * 〈方便利用反射来执行对应方法的中介对象〉
 *
 * @author 坤龙
 * @create 2020/4/1
 * @since 1.0.0
 */
@Data
public class HandlerDefintion {
    /** 所在bean对象 */
    private final Object bean;
    /** 协议所在方法 */
    private final Method method;
    /** 协议字节码文件 */
    private final Class<?> clz;

     private HandlerDefintion(Object bean, Method method, Class<?> clz){
        this.bean = bean;
        this.method = method;
        this.clz = clz;
    }

    /**
     * 创建协议中介对象
     *
     */
    public static HandlerDefintion valueOf(Object bean, Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        return new HandlerDefintion(bean, method, parameterTypes[1]);
    }

    /**
     * 执行该协议所对应的门面类
     *
     */
    public void invoke(Channel channel, Object option){
        ReflectionUtils.makeAccessible(this.method);
        try{
            ReflectionUtils.invokeMethod(this.method, bean, channel, option);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}