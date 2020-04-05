/**
 * FileName: IEventService
 * Author:   坤龙
 * Date:     2020/4/2 11:20
 * Description: 事件接口类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.event.service;

import com.frame.event.IEvent;

/**
 * 〈一句话功能简述〉<br> 
 * 〈事件接口类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public interface IEventService {

    /**
     * 提交同步事件
     */
    void submitSyncEvent(IEvent event);

    /**
     * 提交异步事件
     *
     * @param event
     */
    void submitAsyncEvent(IEvent event);

}