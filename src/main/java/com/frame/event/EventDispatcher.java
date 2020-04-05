/**
 * FileName: EventDispatcher
 * Author:   坤龙
 * Date:     2020/4/2 10:54
 * Description: 事件拍法骑
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    // Map< 事件字节码文件对象 ， 事件反射中介对象 >
    private static final Map<Class<?>, List<EventDefintion>> eventDefintionMap = new HashMap<Class<?>, List<EventDefintion>>();

    /**
     * 注册事件反射中介对象
     *
     * @param method
     * @param defintion
     */
    public static void registerEventDefintion(Method method, EventDefintion defintion){
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<EventDefintion> defintionList = eventDefintionMap.get(parameterTypes[0]);
        if(defintionList == null){
            defintionList = new LinkedList<EventDefintion>();
            eventDefintionMap.put(parameterTypes[0], defintionList);
        }
        defintionList.add(defintion);
    }

    /**
     * 获取所有监听了该事件的地方
     *
     * @param clz
     * @return
     */
    public static List<EventDefintion> getEventDefintionList(Class<?> clz){
        List<EventDefintion> eventDefintionList = eventDefintionMap.get(clz);
        if(eventDefintionList == null){
            logger.error("当字节码为：{}时，无法找到监听的地方", clz);
            throw new RuntimeException();
        }
        return eventDefintionList;
    }

}