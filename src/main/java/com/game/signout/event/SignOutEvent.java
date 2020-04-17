package com.game.signout.event;

import com.frame.event.IEvent;
import lombok.Data;

/**
 * 玩家退出事件
 *
 * @author kolo
 */
@Data
public class SignOutEvent implements IEvent {

    /**  玩家账号 */
    private String accountId;

    public static SignOutEvent valueOf(String accountId){
        SignOutEvent event = new SignOutEvent();
        event.accountId = accountId;
        return event;
    }

    @Override
    public int getKey() {
        return accountId.hashCode();
    }
}
