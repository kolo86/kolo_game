/**
 * FileName: LoginFacade
 * Author:   坤龙
 * Date:     2020/3/31 18:30
 * Description: 玩家登录模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.login.facade;

import com.frame.annotation.EventAnno;
import com.frame.annotation.ReceiverAnno;
import com.game.account.event.CreateAccountAsyncEvent;
import com.game.login.service.ILoginService;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家登录模块〉
 *
 * @author 坤龙
 * @create 2020/3/31
 * @since 1.0.0
 */
@Component
public class LoginFacade {

    @Autowired
    private ILoginService loginService;

    @ReceiverAnno
    public void login(Channel channel, Message.Login message){
        loginService.login(channel, message.getAccount());
    }

    /**
     * 监听玩家创建新账号的事件
     *
     * @param event
     */
    @EventAnno
    public void doCreateAccount(CreateAccountAsyncEvent event){
        loginService.createAccountToLogin(event.getAccount());
    }

}