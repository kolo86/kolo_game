package com.frame.threadpool.account;

import com.frame.threadpool.factoty.NameThreadFactory;

import java.util.concurrent.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈账号线程池〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public class AccountExecutor {

    /** CPU核心数 */
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor[] ACCOUNT_THREAD_POOL_ARR = new ThreadPoolExecutor[CORE_NUM];
    static {
        NameThreadFactory threadFactory = new NameThreadFactory("accountThreadPool");
        for(int i = 0 ; i < CORE_NUM ; i++){
            ACCOUNT_THREAD_POOL_ARR[i] = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), threadFactory, new ThreadPoolExecutor.DiscardPolicy());
            // 启动所有核心线程数
            ACCOUNT_THREAD_POOL_ARR[i].prestartAllCoreThreads();
        }
    }

    /**
     * 执行账号命令
     *
     * @param command
     */
    public static void submit(AbstractAccountCommand command){
        int index = command.modIndex(CORE_NUM);
        ACCOUNT_THREAD_POOL_ARR[index].submit(new Runnable() {
            @Override
            public void run() {
                try{
                    command.action();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}