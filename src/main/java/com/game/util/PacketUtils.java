package com.game.util;

import com.game.account.entity.PlayerEntity;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;
import com.netty.common.ProtocolMsg;
import com.netty.proto.Message;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * 协议包工具类
 *
 */
public class PacketUtils {

    /**
     * 发送一句话给玩家
     *
     * @param channel
     * @param message
     */
    public static void send(Channel channel, String message){
        Message.Option build = Message.Option.newBuilder()
                .setResponse(Message.Response.newBuilder()
                        .setAnswer(message).build())
                .build();

        channel.writeAndFlush(build);
    }

    /**
     * 发送一句话给玩家
     *
     * @param player
     * @param message
     */
    public static void send(PlayerEntity player, String message){
        Message.Option build = Message.Option.newBuilder()
                .setResponse(Message.Response.newBuilder()
                        .setAnswer(message).build())
                .build();

        player.getChannel().writeAndFlush(build);
    }

    /**
     * 发送一句话给所有处于同一场景的玩家
     *
     * @param player
     * @param message
     */
    public static void sendScene(PlayerEntity player, String message){
        AbstractMapHandler handler = SceneType.getSceneById(player.getMapId()).getHandler();
        Map<String, PlayerEntity> accountMap = handler.getAccountMap();
        for(PlayerEntity tempPlayer : accountMap.values()){
            send(tempPlayer, message);
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
}
