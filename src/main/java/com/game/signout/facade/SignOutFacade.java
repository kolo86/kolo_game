package com.game.signout.facade;


import com.frame.dispatcher.anno.ReceiverAnno;
import com.game.signout.service.ISignOutService;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 玩家退出模块
 *
 * @author KOLO
 */
@Component
public class SignOutFacade {

    @Autowired
    private ISignOutService signOutService;

    /**
     * 玩家退出游戏
     *
     * @param channel
     * @param quitMessage
     */
    @ReceiverAnno
    public void signOut(Channel channel, Message.Cm_Quit quitMessage){
        signOutService.signOut(channel);
    }

}
