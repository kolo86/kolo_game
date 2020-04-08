/**
 * FileName: NpcService
 * Author:   坤龙
 * Date:     2020/4/7 15:20
 * Description: Npc服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.npc.service;

import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.npc.constant.NpcEnum;
import com.game.npc.resource.NpcResource;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Npc服务类〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
@Component
public class NpcServiceImpl implements INpcService {

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private NpcManager npcManager;

    @Override
    public void talk(Channel channel, int npcId) {
        PlayerEntity player = playerService.getPlayer(channel);
        if(checkNpcExist(player, npcId)){
            NpcResource npcResource = npcManager.getNpcResource(npcId);

            StringBuilder sb = new StringBuilder();
            sb.append(npcResource.getName()).append(" : ").append(npcResource.getDesc());
            PacketUtils.send(player, sb.toString());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("你当前所在场景【")
                    .append(SceneType.getSceneById(player.getMapId()).getMapName())
                    .append("】，不存在【").append(NpcEnum.getNpcName(npcId)).append("】");

            PacketUtils.send(player, sb.toString());
        }
    }

    /**
     * 检查玩家当前所在的场景中是否存在该NPC
     *
     * @param player
     * @param npcId
     * @return
     */
    private boolean checkNpcExist(PlayerEntity player, int npcId){
        SceneType scene = SceneType.getSceneById(player.getMapId());
        AbstractMapHandler sceneHandler = scene.getHandler();
        return sceneHandler.checkNpcExist(npcId);
    }

}