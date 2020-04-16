package com.game.packback.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.frame.event.anno.EventAnno;
import com.frame.event.impl.OpenServerSyncEvent;
import com.game.packback.service.IBackPackService;
import com.game.role.event.CreateRoleEvent;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 背包门面类
 *
 * @author KOLO
 */
@Component
public class BackpackFacade {

    @Autowired
    private IBackPackService backPackService;

    /**
     * 玩家查看背包数据
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void backPack(Channel channel, Message.BackPack message){
        backPackService.backPack(channel);
    }

    /**
     * 创建角色完成
     *
     * @param event
     */
    @EventAnno
    public void doCreateRole(CreateRoleEvent event){
        backPackService.doCreateRole(event.getAccountId());
    }

    /**
     * 执行开服事件
     *
     */
    @EventAnno
    public void doOpenServer(OpenServerSyncEvent event){
        backPackService.openServer();
    }
}
