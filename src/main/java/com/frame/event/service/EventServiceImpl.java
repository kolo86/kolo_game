package com.frame.event.service;

import com.frame.event.EventDefintion;
import com.frame.event.EventDispatcher;
import com.frame.event.IEvent;
import com.frame.threadpool.factoty.NameThreadFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /** 线程池数目 */
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors() / 2;
    /** 线程池数组 */
    private static final ThreadPoolExecutor[] EVENT_THREAD_POOL_ARR = new ThreadPoolExecutor[CORE_NUM];

    static{
        for(int i = 0 ; i < CORE_NUM ; i++){
            NameThreadFactory threadFactory = new NameThreadFactory("event_thread_pool");
            EVENT_THREAD_POOL_ARR[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(), threadFactory, new ThreadPoolExecutor.DiscardPolicy());
            EVENT_THREAD_POOL_ARR[i].prestartAllCoreThreads();
        }
    }

    /**
     * 提交同步事件
     *
     */
    @Override
    public void submitSyncEvent(IEvent event) {
        execEvent(event);
    }

    /**
     * 提交异步事件
     *
     */
    @Override
    public void submitAsyncEvent(final IEvent event) {
        EVENT_THREAD_POOL_ARR[Math.abs(event.getKey() % CORE_NUM)].submit(() -> execEvent(event));
    }

    /**
     * 执行所有监听了该事件的方法
     *
     */
    private void execEvent(IEvent event){
        List<EventDefintion> defintionList = EventDispatcher.getEventDefintionList(event.getClass());
        for( EventDefintion defintion : defintionList){
            defintion.invoke(event);
        }
    }

}