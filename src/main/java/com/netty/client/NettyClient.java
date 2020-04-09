package com.netty.client;

import com.frame.resource.handler.ResourceCacheHandler;
import com.netty.client.tool.ClientParseToos;
import com.netty.proto.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author kolo
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private final String host;
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = useEpoll() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(useEpoll() ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());

            Channel channel = bootstrap.connect(host, port).sync().channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // 解析中文，找到对应的功能，然后发送协议给服务端
                String order = in.readLine();
                Message.Option option = ClientParseToos.parseStr(order);
                if(option != null){
                    channel.writeAndFlush(option);
                }
            }

        } catch (Exception e) {
            group.shutdownGracefully();
            logger.error("Netty客户端异常关闭！");

            e.printStackTrace();
        }

    }

    private static boolean useEpoll(){
        String os_name = System.getProperty("os.name");
        return os_name != null && os_name.toLowerCase().contains("linux");
    }

    public static void main(String[] args) throws Exception {
        // 初始化配置表数据。由于客户端是使用JAVA代码，所以在处理地图的枚举类型的时候，会像服务端一样初始化，而地图枚举类初始化过程需要用到配置表的数据
        ResourceCacheHandler.init();
        new NettyClient("localhost", 8080).run();
    }

}

