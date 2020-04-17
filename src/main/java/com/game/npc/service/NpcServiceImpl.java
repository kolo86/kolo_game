package com.game.npc.service;

import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.common.constant.I18nId;
import com.game.npc.resource.NpcResource;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import com.netty.common.ProtocolEnum;
import com.netty.proto.Message;
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
            Message.Sm_Talk smTalk = Message.Sm_Talk.newBuilder().setMessage(npcResource.getName() + " : " +npcResource.getDesc()).build();
            PacketUtils.send(player, ProtocolEnum.Sm_Talk.getId() ,smTalk.toByteArray());

        } else {
            PacketUtils.sendResponse(player, I18nId.THE_NPC_DOES_NOT_EXIST );
        }
    }

    /**
     * 检查玩家当前所在的场景中是否存在该NPC
     *
     */
    private boolean checkNpcExist(PlayerEntity player, int npcId){
        SceneType scene = SceneType.getSceneById(player.getMapId());
        AbstractMapHandler sceneHandler = scene.getHandler();
        return sceneHandler.checkNpcExist(npcId);
    }

}