package com.game.login.event;

import com.frame.event.IEvent;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈创建角色后登录完成事件〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
public class CreateRoleLoginEvent implements IEvent {

    private String accountId;

    public static CreateRoleLoginEvent valueOf(String accountId){
        CreateRoleLoginEvent event = new CreateRoleLoginEvent();
        event.accountId = accountId;
        return event;
    }

    @Override
    public int getKey() {
        return accountId.hashCode();
    }
}