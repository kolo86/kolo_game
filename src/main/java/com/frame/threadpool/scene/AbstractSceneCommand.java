package com.frame.threadpool.scene;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象的场景命令〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public abstract class AbstractSceneCommand {
    /** 场景标识 */
    private int key;

    public AbstractSceneCommand(int key){
        this.key = key;
    }

    /**
     * 获取线程池数组下标
     *
     */
    public int modIndex(int coreNum){
        return Math.abs( key % coreNum );
    }

    /**
     * 线程池中需要执行的线程任务
     *
     */
    public abstract void action();

}