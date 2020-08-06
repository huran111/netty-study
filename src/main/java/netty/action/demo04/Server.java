package netty.action.demo04;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:19
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.action.demo04.GroupChatServerChannelInitializer;

public class Server {
    private int port;
    public Server(int port) {
        this.port = port;
    }
    public static void main(String[] args) throws Exception {
        new Server(8000).run();
    }

    public void run() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .channel(NioServerSocketChannel.class)
                    .group(boss, work)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new GroupChatServerChannelInitializer());

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