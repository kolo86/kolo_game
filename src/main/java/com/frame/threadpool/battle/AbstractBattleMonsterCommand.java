package com.frame.threadpool.battle;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象攻击怪物命令〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public abstract class AbstractBattleMonsterCommand {

    /** 标识 */
    private int key;

    public AbstractBattleMonsterCommand(int key){
        this.key = key;
    }

    /**
     * 获取线程池数组的下标
     *
     * @param coreNum
     * @return
     */
    public int modIndex(int coreNum){
        return Math.abs( key % coreNum );
    }

    /**
     * 该命令所执行的任务
     *
     */
    public abstract void action();

}