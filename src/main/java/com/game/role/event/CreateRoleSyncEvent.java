package com.game.role.event;

import com.frame.event.IEvent;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈创建角色完成事件〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
public class CreateRoleSyncEvent implements IEvent {
    /** 账号ID */
    private String accountId;

    public static CreateRoleSyncEvent valueOf(String accountId){
        CreateRoleSyncEvent event = new CreateRoleSyncEvent();
        event.accountId = accountId;
        return event;
    }

    @Override
    public int getKey() {
        return accountId.hashCode();
    }
}