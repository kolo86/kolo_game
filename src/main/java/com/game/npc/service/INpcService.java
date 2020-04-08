/**
 * FileName: INpcService
 * Author:   坤龙
 * Date:     2020/4/7 15:20
 * Description: Npc接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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
     * @param npcId
     */
    void talk(Channel channel, int npcId);

}