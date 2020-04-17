package com.game.login.event;

import com.frame.event.IEvent;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈登录事件〉
 *
 * @author 坤龙
 * @create 2020/4/3
 * @since 1.0.0
 */
@Data
public class LoginEvent implements IEvent {

    private String accountId;

    public static LoginEvent valueOf(String accountId){
        LoginEvent event = new LoginEvent();
        event.accountId = accountId;
        return event;
    }

    @Override
    public int getKey() {
        return accountId.hashCode();
    }
}