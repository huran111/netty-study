package netty.guigu.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.guigu.netty.chat.GroupChatServer;
import netty.guigu.netty.chat.GroupChatServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 14:21
 */
public class NettyWebSocketServer {
    private int port;

    public NettyWebSocketServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        ServerBootstrap b = new ServerBootstrap();
        b.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(3, 5, 7, TimeUnit.MINUTES));
                        //基于Http协议  使用http的编码和解码器
                        ch.pipeline().addLast(new HttpServerCodec());
                        //添加以块的方式
                        ch.pipeline().addLast(new ChunkedWriteHandler());
                        //http数据传输是分段的， 可以聚合多个段，
                        ch.pipeline().addLast(new HttpObjectAggregator(8192));
                        //将一个http协议升级为ws协议
                        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/hello"));
                        ch.pipeline().addLast(new NettyWebSocketServerHandler());
                    }
                });
        final ChannelFuture sync = b.bind(port).sync();
        try {
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyWebSocketServer groupCharServer = new NettyWebSocketServer(2222);
        try {
            groupCharServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}