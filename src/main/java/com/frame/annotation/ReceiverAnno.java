/**
 * FileName: ReceiverAnno
 * Author:   坤龙
 * Date:     2020/4/1 17:14
 * Description: 接收协议的注解
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.annotation;

import java.lang.annotation.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈接收协议的注解〉
 *
 * @author 坤龙
 * @create 2020/4/1
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface ReceiverAnno {

}