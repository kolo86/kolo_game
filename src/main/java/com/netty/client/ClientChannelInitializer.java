package com.netty.client;

import com.netty.proto.Message;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

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
        pipeline.addLast("encoder", new ProtobufEncoder());
        pipeline.addLast("decoder", new ProtobufDecoder(Message.Option.getDefaultInstance()));
        pipeline.addLast("handler", new ClientHandler());
    }
}
