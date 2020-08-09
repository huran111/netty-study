package netty.guigu.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import netty.guigu.nio.groupchat.GroupCharServer;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 12:22
 */
public class GroupChatServer {
    private int port;
    public GroupChatServer(int port){
        this.port=port;
    }
    public void run() throws InterruptedException {
        EventLoopGroup boosGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup(8);
        ServerBootstrap b=new ServerBootstrap();
        b.group(boosGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));

                        ch.pipeline().addLast("decoder",new StringDecoder());
                        ch.pipeline().addLast("encoder",new StringEncoder());
                        ch.pipeline().addLast("serverhandler",new GroupChatServerHandler());
                    }
                });
        final ChannelFuture sync = b.bind(port).sync();
        try {
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupCharServer=new GroupChatServer(2222);
        try {
            groupCharServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}