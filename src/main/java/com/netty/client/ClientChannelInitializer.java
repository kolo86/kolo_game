package com.netty.client;

import com.netty.common.ProtocolDecoder;
import com.netty.common.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author kolo
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 设置客户端IO事件的处理类，用于记录日志、对消息进行编解码等功能
     */
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new ProtocolDecoder());
        pipeline.addLast("encoder", new ProtocolEncoder());
        pipeline.addLast("handler", new ClientHandler());
    }
}
