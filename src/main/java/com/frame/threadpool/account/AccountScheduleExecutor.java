package com.frame.threadpool.account;

import com.frame.threadpool.factoty.NameThreadFactory;

import java.util.concurrent.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈账号定时线程池〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public class AccountScheduleExecutor {
    /** 核心线程数 */
    private static final int THREAD_POOL_NUM = Runtime.getRuntime().availableProcessors() / 2;
    /** 定时线程池 */
    private static final ScheduledExecutorService[] SCHEDULE_THREAD_POOL_ARR = new ScheduledExecutorService[THREAD_POOL_NUM];
    /** 核心线程数 */
    private static final int CORE_NUM = 1;
    static{
        for(int i = 0 ; i < THREAD_POOL_NUM ; i++){
            NameThreadFactory threadFactory = new NameThreadFactory("accountSchedule");
            SCHEDULE_THREAD_POOL_ARR[i] = new ScheduledThreadPoolExecutor(CORE_NUM, threadFactory, new ThreadPoolExecutor.DiscardPolicy());
        }
    }

    /**
     * 提交定时执行的账号任务
     *
     */
    public static void submit(AbstractScheduleAccountCommand command){
        int index = command.modIndex(THREAD_POOL_NUM);
        SCHEDULE_THREAD_POOL_ARR[index].scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try{
                    command.action();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, command.getInitialDalay(), command.getDelay(), TimeUnit.MILLISECONDS);
    }


}