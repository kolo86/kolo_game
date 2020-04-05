package com.game.util;

import com.game.account.entity.PlayerEntity;
import com.netty.proto.Message;
import io.netty.channel.Channel;

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
                .setOptionType(Message.Option.OptionType.RESPONSE)
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
                .setOptionType(Message.Option.OptionType.RESPONSE)
                .setResponse(Message.Response.newBuilder()
                        .setAnswer(message).build())
                .build();

        player.getChannel().writeAndFlush(build);
    }
}
