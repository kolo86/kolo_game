/**
 * FileName: EventAnno
 * Author:   坤龙
 * Date:     2020/4/2 10:37
 * Description: 事件注解
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.annotation;

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