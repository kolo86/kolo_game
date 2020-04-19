package com.game.backpack.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.frame.event.anno.EventAnno;
import com.frame.event.impl.OpenServerSyncEvent;
import com.game.backpack.service.IBackPackService;
import com.game.role.event.CreateRoleSyncEvent;
import com.netty.proto.Message;
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
     */
    @ReceiverAnno
    public void backPack(Channel channel, Message.Cm_BackPack message){
        backPackService.backPack(channel);
    }

    /**
     * 创建角色完成
     *
     */
    @EventAnno
    public void doCreateRole(CreateRoleSyncEvent event){
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
