/**
 * FileName: EventServiceImpl
 * Author:   坤龙
 * Date:     2020/4/2 11:24
 * Description: 事件服务实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.event.service;

import com.frame.event.EventDefintion;
import com.frame.event.EventDispatcher;
import com.frame.event.IEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈事件服务实现类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class EventServiceImpl implements IEventService {

    @Override
    public void submitSyncEvent(IEvent event) {
        execEvent(event);
    }

    @Override
    public void submitAsyncEvent(final IEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                execEvent(event);
            }
        }).start();
    }

    /**
     * 执行所有监听了该事件的方法
     *
     * @param event
     */
    private void execEvent(IEvent event){
        List<EventDefintion> defintionList = EventDispatcher.getEventDefintionList(event.getClass());
        for( EventDefintion defintion : defintionList){
            defintion.invoke(event);
        }
    }

}