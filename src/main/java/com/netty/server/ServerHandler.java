package com.netty.server;

import com.frame.dispatcher.ActionDispatcher;
import com.frame.dispatcher.HandlerDefintion;
import com.netty.common.ProtocolMsg;
import com.netty.util.ProtocolParseUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kolo
 */
public class ServerHandler extends SimpleChannelInboundHandler<ProtocolMsg> {

    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMsg msg) throws Exception {
        Object agreement = ProtocolParseUtils.getProtocolObj(msg);
        HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(agreement.getClass());
        handlerDefintion.invoke(ctx.channel(), agreement);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        logger.warn("客户端退出 : {} ",cause.getMessage());
        /**
         * 发生异常时，关闭ChannelHandlerContext
         */
        ctx.close();
    }
}
