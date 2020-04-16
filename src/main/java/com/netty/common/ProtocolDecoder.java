package com.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈协议解码器〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);

    private static final int HEADER_SIZE = 8;
    private static final int MAX_FRAME_LENGTH = 1024 * 1024 * 10;
    private static final int LENGTH_FIELD_OFFSET = 0;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_ADJUSTMENT = 4;
    private static final int INITIAL_BYTES_TO_STRIP = 0;
    private static final boolean FAILFAST = true;

    /**
     * @param maxFrameLength      帧的最大长度
     * @param lengthFieldOffset   length字段偏移的地址
     * @param lengthFieldLength   length字段所占的字节长
     * @param lengthAdjustment    修改帧数据长度字段中定义的值，可以为负数 因为有时候我们习惯把头部记入长度,若为负数,则说明要推后多少个字段
     * @param initialBytesToStrip 解析时候跳过多少个长度
     * @param failFast            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异
     */
    public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public ProtocolDecoder(){
        this(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, FAILFAST);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < HEADER_SIZE) {
            logger.error("本次协议的字节数小于{}个字节", HEADER_SIZE);
            return null;
        }

        //读取dataLength字段
        int dataLength = in.readInt();
        //读取code字段
        int code = in.readInt();

        if (in.readableBytes() != dataLength) {
            logger.error("协议传来的长度与实际长度不符合!");
            return null;
        }
        //读取真正的数据
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);

        return ProtocolMsg.valueOf(code, bytes);
    }
}
