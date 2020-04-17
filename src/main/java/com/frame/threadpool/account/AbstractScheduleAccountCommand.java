package com.frame.threadpool.account;

import com.frame.threadpool.AbstractCommand;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象账号定时任务〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
@Data
public abstract class AbstractScheduleAccountCommand extends AbstractCommand {
    /** 账号ID */
    private String accountId;
    /** 初始化延迟时间 */
    private long initialDalay;
    /** 间隔执行的时间 */
    private long delay;

    public AbstractScheduleAccountCommand(String accountId, long initialDalay, long delay){
        this.accountId = accountId;
        this.initialDalay = initialDalay;
        this.delay = delay;
    }

    /**
     * 获取该玩家所在的线程池下标
     *
     */
    public int modIndex(int threadPoolNum){
        return Math.abs(accountId.hashCode() % threadPoolNum);
    }

    /**
     * 定时任务需要执行的操作
     *
     */
    public abstract void action();

}