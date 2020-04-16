package com.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 〈一句话功能简述〉<br>
 * 〈协议编码器〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
public class ProtocolEncoder extends MessageToByteEncoder<ProtocolMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolMsg msg, ByteBuf out) throws Exception {
        if(msg == null){
            throw new Exception("协议为空！");
        }
        int dataLength = msg.getData() == null ? 0 : msg.getData().length;
        out.ensureWritable(4 + dataLength);

        out.writeInt(dataLength);
        out.writeInt(msg.getCode());
        if(dataLength > 0){
            out.writeBytes(msg.getData());
        }
    }

}