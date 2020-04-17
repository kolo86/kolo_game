package com.frame.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈协议派发器〉
 *
 * @author 坤龙
 * @create 2020/4/1
 * @since 1.0.0
 */
public class ActionDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(ActionDispatcher.class);

    /** Map < 协议字节码文件， 协议中介对象> */
    public static final Map<Class<?>, HandlerDefintion> agreeHandlerMap = new HashMap<>();

    public static void registerAgreeMap(Class<?> clz, HandlerDefintion beanDefintion){
        agreeHandlerMap.put(clz, beanDefintion);
    }

    public static HandlerDefintion getHandlerDefintion(Class<?> clz){
        HandlerDefintion refreshObject = agreeHandlerMap.get(clz);
        if(refreshObject == null){
            logger.error("当协议为：{}，无法找到对应的 HandlerDefintion！", clz);
            throw new RuntimeException();
        }
        return refreshObject;
    }
}