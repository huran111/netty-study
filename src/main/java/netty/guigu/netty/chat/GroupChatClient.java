package netty.guigu.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 12:22
 */
public class GroupChatClient {
    private int port;
    private String host;
    public GroupChatClient(String host,int port){
        this.host=host;
        this.port=port;
    }
    public void run() throws InterruptedException {
        EventLoopGroup boosGroup=new NioEventLoopGroup(1);

        try {
            Bootstrap b=new Bootstrap()
                    .group(boosGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder",new StringDecoder());
                            ch.pipeline().addLast("encoder",new StringEncoder());
                            ch.pipeline().addLast("clienthandler",new GroupChatClientHandler());
                        }
                    });
            final ChannelFuture sync = b.connect(host, port).sync();
            final Channel channel = sync.channel();
//            Scanner scanner=new Scanner(System.in);
//            while (scanner.hasNextLine()){
//                final String s = scanner.nextLine();
//                channel.writeAndFlush(s+"\r\n");
//            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        final GroupChatClient groupChatClient = new GroupChatClient("127.0.0.1", 2222);
        try {
            groupChatClient.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}