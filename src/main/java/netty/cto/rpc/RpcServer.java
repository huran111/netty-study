package netty.cto.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-17 22:55
 */
public class RpcServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        //worker是处理io事件  一般设置为256-512之间
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(new ObjectEncoder());      ;
                            ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)));

                            ch.pipeline().addLast(new ServerMsgHandler());

                        }
                    });

            ChannelFuture f = b.bind(8888).sync(); // (7)
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}