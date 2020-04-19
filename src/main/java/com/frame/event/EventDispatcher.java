package com.frame.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈事件派发器〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public class EventDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    /** Map< 事件字节码文件对象 ， 事件反射中介对象 > */
    private static final Map<Class<?>, List<EventDefintion>> EVENT_DEFINTION_MAP = new HashMap<>();

    /**
     * 注册事件反射中介对象
     *
     */
    public static void registerEventDefintion(Method method, EventDefintion defintion){
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<EventDefintion> defintionList = EVENT_DEFINTION_MAP.computeIfAbsent(parameterTypes[0], k -> new LinkedList<>());
        defintionList.add(defintion);
    }

    /**
     * 对所有监听了事件的地方做排序
     *
     */
    public static void sort(){
        for( List<EventDefintion> list:  EVENT_DEFINTION_MAP.values()){
            Collections.sort(list);
        }
    }

    /**
     * 获取所有监听了该事件的地方
     *
     */
    public static List<EventDefintion> getEventDefintionList(Class<?> clz){
        List<EventDefintion> eventDefintionList = EVENT_DEFINTION_MAP.get(clz);
        if(eventDefintionList == null){
            logger.error("当字节码为：{}时，无法找到监听的地方", clz);
            throw new RuntimeException();
        }
        return eventDefintionList;
    }

}