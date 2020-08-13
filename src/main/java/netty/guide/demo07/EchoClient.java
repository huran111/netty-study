package netty.guide.demo07;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 10:28
 */
public class EchoClient {
    public void bind(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,
                                    2,0,2));
                           ch.pipeline().addLast("msgpack decoder", new MsgPackDecode());
                            ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder", new MsgPackEncode());
                            ch.pipeline().addLast(new EchoClientHandler());


                        }
                    });
            ChannelFuture sync = b.connect(host, port).sync();
            sync.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 2222;
        new EchoClient().bind("127.0.0.1", port);
    }
}