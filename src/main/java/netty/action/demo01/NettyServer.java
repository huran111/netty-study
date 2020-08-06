package netty.action.demo01;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:00
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {

    public static void main(String[] args) throws Exception {

        // 1.创建 BossGroup 和 workerGroup
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // 2.创建服务器端的启动对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 3.链式编程，配置参数
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持获得连接状态
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 给 WorkerGroup 的 EventLoop 对应的管道设置处理器
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                });

        // 4.绑定端口,运行服务器
        ChannelFuture future = serverBootstrap.bind(8000).sync();
        System.out.println("server started and listen " + 8000);

        // 5.对关闭通道进行监听
        future.channel().closeFuture().sync();
    }
}