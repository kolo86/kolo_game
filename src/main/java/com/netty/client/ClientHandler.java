package com.netty.client;

import com.netty.common.ProtocolEnum;
import com.netty.common.ProtocolMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kolo
 * SimpleChannelInboundHandler<Message.Option>
 */
public class ClientHandler extends SimpleChannelInboundHandler<ProtocolMsg> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class.getName());

    /**
     * 发送消息过去给服务端
     * 当客户端和服务端连接成功之后，Netty的NIO线程会调用channelActive方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("\n******************欢迎进入游戏空间************************\n" +
                "请输入你的功能：\n" +
                "【注册账号】\n" +
                "\t请输入：注册 账号 昵称\n" +
                "\t  例子：注册 kolo 孙悟空\n" +
                "\n" +
                "【登录游戏】\n" +
                "\t请输入：登录 账号\n" +
                "\t  例子：登录 kolo");
    }

    /**
     * 读取服务端返回的数据
     * 当服务端返回应答消息时，channelRead方法会被调用
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ProtocolMsg msg) {
        int code = msg.getCode();
        ProtocolEnum protocol = ProtocolEnum.getProtocol(code);
        protocol.printMsg(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        logger.warn("服务端已关闭 : {} ",cause.getMessage());
        ctx.close();
    }

}
