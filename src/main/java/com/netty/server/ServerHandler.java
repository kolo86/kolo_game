package com.netty.server;

import com.frame.dispatcher.ActionDispatcher;
import com.frame.dispatcher.HandlerDefintion;
import com.netty.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kolo
 */
public class ServerHandler extends SimpleChannelInboundHandler<Message.Option> {

    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message.Option msg) throws Exception {

        if(msg.getOptionType() == Message.Option.OptionType.REGISTER){
            Message.Register register = msg.getRegister();
            HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(register.getClass());
            handlerDefintion.invoke(ctx.channel(), register);

        } else if(msg.getOptionType() == Message.Option.OptionType.ROLE){
            Message.CreateRole createRole = msg.getCreatRole();
            HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(createRole.getClass());
            handlerDefintion.invoke(ctx.channel(), createRole);

        } else if(msg.getOptionType() == Message.Option.OptionType.LOGIN){
            Message.Login login = msg.getLogin();
            HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(login.getClass());
            handlerDefintion.invoke(ctx.channel(), login);

        } else if(msg.getOptionType() == Message.Option.OptionType.CHANGE_MAP){
            Message.ChangeMap change_map = msg.getChangeMap();
            HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(change_map.getClass());
            handlerDefintion.invoke(ctx.channel(), change_map);

        }else if(msg.getOptionType() == Message.Option.OptionType.STATE){
            Message.State state = msg.getState();
            HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(state.getClass());
            handlerDefintion.invoke(ctx.channel(), state);

        } else if(msg.getOptionType() == Message.Option.OptionType.QUIT){
            Message.Quit quit = msg.getQuit();
            HandlerDefintion handlerDefintion = ActionDispatcher.getHandlerDefintion(quit.getClass());
            handlerDefintion.invoke(ctx.channel(), quit);

        }else{
            logger.error("传入的协议类型不对应！！！");
        }
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
