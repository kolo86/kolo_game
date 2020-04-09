package com.frame.threadpool.account;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象的账号线程任务命令〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public abstract class AbstractAccountCommand {
    /** 玩家账号ID */
    private String accountId;

    AbstractAccountCommand(String accountId){
        this.accountId = accountId;
    }

    /**
     * 获取该账号的线程池下标
     *
     * @param coreNum
     * @return
     */
    public int modIndex(int coreNum){
        return Math.abs(accountId.hashCode() % coreNum);
    }

    /**
     * 该命令所执行的任务
     *
     */
    public abstract void action();

}