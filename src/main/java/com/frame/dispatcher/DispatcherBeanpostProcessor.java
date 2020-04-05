/**
 * FileName: DispatcherBeanpostProcessor
 * Author:   坤龙
 * Date:     2020/4/1 17:10
 * Description: 加载类对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.dispatcher;

import com.frame.annotation.ReceiverAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br> 
 * 〈加载类对象后，记录类的信息〉
 *
 * @author 坤龙
 * @create 2020/4/1
 * @since 1.0.0
 */
@Component
public class DispatcherBeanpostProcessor implements BeanPostProcessor , Ordered {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherBeanpostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] methods = beanClass.getDeclaredMethods();
        for( Method method :  methods){
            if(method.isAnnotationPresent(ReceiverAnno.class)){
                HandlerDefintion handlerBeanDefintion = HandlerDefintion.valueOf(bean, method);
                ActionDispatcher.registerAgreeMap(handlerBeanDefintion.getClz(), handlerBeanDefintion);
            }
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}