package com.game.util;

import com.game.account.entity.PlayerEntity;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;
import com.netty.common.ProtocolEnum;
import com.netty.common.ProtocolMsg;
import com.netty.proto.Message;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * 协议包工具类
 *
 * @author kolo
 */
public class PacketUtils {

    /**
     * 发送一句话给所有处于同一场景的玩家
     *
     */
    public static void sendScene(PlayerEntity player, int code, byte[] data){
        AbstractMapHandler handler = SceneType.getSceneById(player.getMapId()).getHandler();
        Map<String, PlayerEntity> accountMap = handler.getAccountMap();
        for(PlayerEntity tempPlayer : accountMap.values()){
            send(tempPlayer, code, data);
        }
    }

    /**
     * 发送协议给玩家
     *
     * @param channel
     * @param msg
     */
    public static void send(Channel channel, ProtocolMsg msg){
        channel.writeAndFlush(msg);
    }

    /**
     * 发送I18N消息
     *
     */
    public static void sendResponse(Channel channel, int i18nId){
        Message.Response response = Message.Response.newBuilder()
                .setSuccess(true).setResponseId(i18nId).build();
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(ProtocolEnum.Response.getId(), response.toByteArray());
        channel.writeAndFlush(protocolMsg);
    }

    /**
     * 发送I18N消息
     *
     */
    public static void sendResponse(PlayerEntity player, int i18nId){
        Message.Response response = Message.Response.newBuilder()
                .setSuccess(true).setResponseId(i18nId).build();
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(ProtocolEnum.Response.getId(), response.toByteArray());
        player.getChannel().writeAndFlush(protocolMsg);
    }

    /**
     * 发送协议数据给玩家
     *
     * @param player
     * @param code
     * @param data
     */
    public static void send(PlayerEntity player, int code, byte[] data){
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(code, data);
        player.getChannel().writeAndFlush(protocolMsg);
    }

}
