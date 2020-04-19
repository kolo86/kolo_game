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

    /** I18N响应类型 */
    public static final int I18N_TYPE = 1;
    /** 字符串响应类型 */
    public static final int ANSWER_TYPE = 2;

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
     * 发送I18N消息
     *
     */
    public static void sendResponse(Channel channel, int i18nId){
        Message.Response response = Message.Response.newBuilder()
                .setType(PacketUtils.I18N_TYPE).setResponseId(i18nId).build();
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(ProtocolEnum.Response.getId(), response.toByteArray());
        channel.writeAndFlush(protocolMsg);
    }

    /**
     * 发送I18N消息
     *
     */
    public static void sendResponse(PlayerEntity player, int i18nId){
        Message.Response response = Message.Response.newBuilder()
                .setType(PacketUtils.I18N_TYPE).setResponseId(i18nId).build();
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(ProtocolEnum.Response.getId(), response.toByteArray());
        player.getChannel().writeAndFlush(protocolMsg);
    }

    /**
     * 发送字符串的响应消息给玩家
     *
     * @param player
     * @param answer
     */
    public static void sendResponse(PlayerEntity player, String answer){
        Message.Response response = Message.Response.newBuilder()
                .setType(PacketUtils.ANSWER_TYPE).setAnswer(answer).build();
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(ProtocolEnum.Response.getId(), response.toByteArray());
        player.getChannel().writeAndFlush(protocolMsg);
    }

    /**
     * 发送协议数据给玩家
     *
     */
    public static void send(PlayerEntity player, int code, byte[] data){
        ProtocolMsg protocolMsg = ProtocolMsg.valueOf(code, data);
        player.getChannel().writeAndFlush(protocolMsg);
    }

}
