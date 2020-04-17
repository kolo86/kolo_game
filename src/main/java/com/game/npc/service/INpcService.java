package com.game.npc.service;

import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Npc接口〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public interface INpcService {

    /**
     * 玩家跟NPC对话
     *
     */
    void talk(Channel channel, int npcId);

}