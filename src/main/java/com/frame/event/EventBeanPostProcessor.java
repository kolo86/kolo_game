/**
 * FileName: EventBeanPostProcessor
 * Author:   坤龙
 * Date:     2020/4/2 10:46
 * Description: 事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.event;

import com.frame.annotation.EventAnno;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br> 
 * 〈加载类对象后，查看有无加事件注解的方法〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class EventBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] methods = beanClass.getDeclaredMethods();
        for( Method method : methods){
            if(method.isAnnotationPresent(EventAnno.class)){
                EventDefintion eventDefintion = EventDefintion.valueOf(bean, method);
                EventDispatcher.registerEventDefintion(method, eventDefintion);
            }
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}