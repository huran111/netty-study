package netty.action.demo06;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 10:07
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServer {
    private int port;
    public WSServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new WSServer(8002).run();
    }

    public void run() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 因为基于 HTTP 协议，使用 http 的编码解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 以块的方式写
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 因为 http 数据在传输过程中时分段的，HttpObjectAggregator 就可以将多个段聚合
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            // websocket 数据是以帧（frame）的形式传递
                            // webSocketFrame 下面有六个子类
                            // WebSocketServerProtocolHandler：将 http 协议升级为 ws 协议，保持长连接
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new WSServerHandler());
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
