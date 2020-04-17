package com.frame.threadpool.battle;

import com.frame.threadpool.factoty.NameThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈战斗线程池〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public class BattleExecutor {

    /** 核心线程数 */
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors();
    /** 线程池数组 */
    private static final ThreadPoolExecutor[] BATTLE_THREAD_POOL_ARR = new ThreadPoolExecutor[CORE_NUM];

    static {
        for(int i = 0 ; i < CORE_NUM ; i++ ){
            NameThreadFactory threadFactory = new NameThreadFactory("battle_thread_pool");
            BATTLE_THREAD_POOL_ARR[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(), threadFactory, new ThreadPoolExecutor.DiscardPolicy());
            BATTLE_THREAD_POOL_ARR[i].prestartAllCoreThreads();
        }
    }

    /**
     * 提交战斗命令
     *
     */
    public static void submit(AbstractBattleMonsterCommand command){
        int index = command.modIndex(CORE_NUM);
        BATTLE_THREAD_POOL_ARR[index].submit(() -> {
            try{
                command.action();
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

}