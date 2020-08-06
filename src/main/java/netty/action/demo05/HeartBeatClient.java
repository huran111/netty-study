package netty.action.demo05;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 10:03
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class HeartBeatClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8001;

    public static void main(String[] args) {
        new HeartBeatClient().run(HOST, PORT);
    }

    public void run(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder()); // 解码器
                            pipeline.addLast("encoder", new StringEncoder()); // 编码器
                            ch.pipeline().addLast(new HeartBeatClientHandler());
                        }
                    });

            ChannelFuture future = client.connect(host, port).sync();
            System.out.println("--------------" + future.channel().localAddress() + "--------------");

            Channel channel = future.channel();
            // 客户端输入信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}