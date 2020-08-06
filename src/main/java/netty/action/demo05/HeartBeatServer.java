package netty.action.demo05;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 09:57
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class HeartBeatServer {
    private int port;
    public HeartBeatServer(int port) {
        this.port = port;
    }
    public static void main(String[] args) throws Exception {
        new HeartBeatServer(8001).run();
    }
    public void run() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .channel(NioServerSocketChannel.class)
                    .group(boss, work)
                    .handler(new LoggingHandler(LogLevel.DEBUG)) // 在 bossGroup 添加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /*
                             * IdleStateHandler: netty 提供的处理空闲状态的处理器
                             * long readerIdleTime: 多长时间没有读，就会发送一个心跳检查是否连接
                             * long writerIdleTime: 多长时间没有写，就会发送一个心跳检查是否连接
                             * long allIdleTime: 多长时间没有读写，就会发送一个心跳检查是否连接
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 当 IdleStateEvent 触发后，就会传递给管道的下一个 handler 进行处理
                            // 通过调用（触发）下一个 handler 的 userEventTriggered()
                            // 添加对空闲检测进一步处理的自定义 handler
                            pipeline.addLast(new HeartBeatServerHandler());
                        }
                    });

            // 绑定端口，启动服务
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("server started and listen " + port);
            // 监听关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}

