package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author 坤龙
 */
@Component
public class NettyServer {

    private Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private static final EventLoopGroup bossGroup = useEpoll() ? new EpollEventLoopGroup(4) : new NioEventLoopGroup(4);
    private static final EventLoopGroup workerGroup = useEpoll() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
    private Channel channel;

    private String url;
    private int port;

    NettyServer(){
        url = "127.0.0.1";
        port = 8080;
    }

    @PostConstruct
    public void run(){
        ChannelFuture future = null;
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(useEpoll()? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ServerChannelInitializerHandler());

            InetSocketAddress address = new InetSocketAddress(url, port);
            future = bootstrap.bind(address).sync();
            channel = future.channel();
            logger.info("Netty服务启动成功！");
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            logger.error("Netty启动失败！地址：{}，端口号：{}！", url, port);
            e.printStackTrace();
        }
    }

    public void destroy(){
        if(channel != null){
            channel.close();
        }

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.info("Netty服务关闭！");
    }

    private static boolean useEpoll(){
        String os_name = System.getProperty("os.name");
        return os_name != null && os_name.toLowerCase().contains("linux");
    }

    public static void main(String[] args) {
        NettyServer service = new NettyServer();
        service.run();
    }

}
