package netty.cto.demo10;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 14:32
 */
public class RpcClient {
    public void connect(int port,String host) throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                        //    ch.pipeline().addLast(new ObjectEncoder());
                         //   ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)));
                            ch.pipeline().addLast(new ClientMsgHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new RpcClient().connect(8080,"127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}