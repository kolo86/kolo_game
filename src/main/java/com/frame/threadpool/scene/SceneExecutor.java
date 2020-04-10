package com.frame.threadpool.scene;

import com.frame.threadpool.factoty.NameThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈场景线程池〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public class SceneExecutor {
    /** 核心线程数 */
    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors();
    /** 场景线程池数组 */
    private static final ThreadPoolExecutor[] SCENE_THREAD_POOL = new ThreadPoolExecutor[CORE_NUM];

    static{
        for(int i = 0 ; i < CORE_NUM ; i++){
            NameThreadFactory threadFactory = new NameThreadFactory("scene_thread_pool");
            SCENE_THREAD_POOL[i] = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),threadFactory, new ThreadPoolExecutor.DiscardPolicy());
            SCENE_THREAD_POOL[i].prestartAllCoreThreads();
        }
    }

    /**
     * 提交场景命令
     *
     * @return
     */
    public static void submit(AbstractSceneCommand command){
        int index = command.modIndex(CORE_NUM);
        SCENE_THREAD_POOL[index].submit(new Runnable() {
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