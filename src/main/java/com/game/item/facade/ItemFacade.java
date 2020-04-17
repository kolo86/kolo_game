package com.game.item.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.game.item.service.IItemService;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈物品门面类〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Component
public class ItemFacade {

    @Autowired
    private IItemService itemService;

    /**
     * 使用道具
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void useItem(Channel channel, Message.Cm_UseItem message){
        itemService.useItem(channel, message.getItemOnlyId());
    }

}