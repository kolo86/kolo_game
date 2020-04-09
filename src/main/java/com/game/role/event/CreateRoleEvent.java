/**
 * FileName: CreateRoleEvent
 * Author:   坤龙
 * Date:     2020/4/2 16:43
 * Description: 创建角色完成事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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
public class CreateRoleEvent implements IEvent {
    /** 账号ID */
    private String accountId;

    public static CreateRoleEvent valueOf(String accountId){
        CreateRoleEvent event = new CreateRoleEvent();
        event.accountId = accountId;
        return event;
    }

}