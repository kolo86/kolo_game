package com.frame.resource.anno;

import java.lang.annotation.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈配置表注解〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Resource {

}