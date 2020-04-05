/**
 * FileName: LoginEvent
 * Author:   坤龙
 * Date:     2020/4/2 15:43
 * Description: 登录事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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

}