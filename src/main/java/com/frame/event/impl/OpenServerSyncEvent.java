/**
 * FileName: OpenServerSyncEvent
 * Author:   坤龙
 * Date:     2020/4/2 11:49
 * Description: 开服同步事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.event.impl;

import com.frame.event.IEvent;

/**
 * 〈一句话功能简述〉<br> 
 * 〈开服同步事件〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public class OpenServerSyncEvent implements IEvent {

    public static OpenServerSyncEvent valueOf(){
        return new OpenServerSyncEvent();
    }
}