package netty.action.demo02;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:05
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class NettyClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT= 8080;

    public static void main(String[] args){
        new NettyClient().start(HOST, PORT);
    }

    public void start(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //.addLast("decoder", new StringDecoder())
                                    //.addLast("encoder", new StringEncoder())
                                    .addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture future = client.connect(host, port).sync();
            //future.channel().writeAndFlush("Hello Netty Server ,I am a netty client");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class NettyClientHandler extends ChannelInboundHandlerAdapter {
        /**
         * 通道就绪触发该方法
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("HelloWorldClientHandler Active");
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 服务器", CharsetUtil.UTF_8));
        }

        /**
         * 当通道有读取事件时触发该方法
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("收到服务器响应： " + buf.toString(CharsetUtil.UTF_8));
        }
    }
}