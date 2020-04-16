/**
 * FileName: NpcFacade
 * Author:   坤龙
 * Date:     2020/4/7 15:20
 * Description: NPC门面类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.npc.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.game.npc.service.INpcService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈NPC门面类〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
@Component
public class NpcFacade {

    @Autowired
    private INpcService npcService;

    /**
     * 玩家跟NPC对话
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void talk(Channel channel, Message.Talk message){
        npcService.talk(channel, message.getNpcId());
    }


}