package com.frame.event.anno;

import java.lang.annotation.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈事件注解〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface EventAnno {

}