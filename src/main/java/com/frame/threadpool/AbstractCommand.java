package com.frame.threadpool;

import lombok.Data;

import java.util.concurrent.ScheduledFuture;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象命令〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Data
public abstract class AbstractCommand {

    /** 是否已经被取消 */
    private boolean isCannel = false;
    /** 任务句柄 */
    private ScheduledFuture future;

    /**
     * 取消任务
     *
     */
    public void cancel(){
        if(future != null){
            future.cancel(true);
        }
        isCannel = true;
    }

}