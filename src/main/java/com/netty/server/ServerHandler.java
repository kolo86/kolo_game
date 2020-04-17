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
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMsg msg) {
        Object agreement = ProtocolParseUtils.getProtocolObj(msg);
        HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(agreement.getClass());
        try{
            handlerDefintion.invoke(ctx.channel(), agreement);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        logger.warn("客户端退出 : {} ",cause.getMessage());
        ctx.close();
    }
}
