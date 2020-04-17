package com.game.account.event;

import com.frame.event.IEvent;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈创建账号完成事件〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
public class CreateAccountAsyncEvent implements IEvent {

    /** 玩家账号 */
    private String account;

    public static CreateAccountAsyncEvent valueOf(String account){
        CreateAccountAsyncEvent accountAsyncEvent = new CreateAccountAsyncEvent();
        accountAsyncEvent.account = account;
        return accountAsyncEvent;
    }

    @Override
    public int getKey() {
        return account.hashCode();
    }

}