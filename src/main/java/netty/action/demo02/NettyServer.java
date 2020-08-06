package netty.action.demo02;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:04
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class NettyServer {

    private int port;

    public static void main(String[] args) {
        new NettyServer(8080).start();
    }

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() {
        /**
         * 创建两个EventLoopGroup，即两个线程池，boss线程池用于接收客户端的连接，一个线程监听一个端口，一般只会监听一个端口所以只需一个线程
         * work池用于处理网络连接数据读写或者后续的业务处理（可指定另外的线程处理业务，work完成数据读写）
         */
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            /**
             * 实例化一个服务端启动类，
             * group（）指定线程组
             * channel（）指定用于接收客户端连接的类，对应java.nio.ServerSocketChannel
             * childHandler（）设置编码解码及处理连接的类
             */
            ServerBootstrap server = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //.addLast("decoder", new StringDecoder())
                                    //.addLast("encoder", new StringEncoder())
                                    .addLast(new NettyServerHandler2());
                        }
                    });

            // 绑定端口
            final ChannelFuture future = server.bind().sync();
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (future.isDone()) {
                        System.out.println("监听端口 8080 成功");
                    } else {
                        System.out.println("监听端口 8080 失败");
                    }
                }
            });
            System.out.println("server started and listen " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static class NettyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("HelloWorldServerHandler active");
        }

        /**
         * 读取客户端发送的数据
         * ChannelHandlerContext ctx： 上下文对象，含有管道 pipeline，通道 channel，连接地址
         * Object msg: 客户端发送的数据
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server channelRead...");

            // 读取客户端发送的数据
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("from " + ctx.channel().remoteAddress() + ", " + buf.toString(CharsetUtil.UTF_8));

            //System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
            // 返回消息
            //ctx.write("server write, 收到消息" + msg);
            //ctx.flush();
        }

        /**
         * 数据读取完毕
         */
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端", CharsetUtil.UTF_8));
        }

        /**
         * 处理异常，关闭通道
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.channel().close();
        }
    }
}