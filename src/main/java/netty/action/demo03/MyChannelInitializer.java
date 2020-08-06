package netty.action.demo03;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:16
 */
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        /* 向管道加入处理器 */
        ChannelPipeline pipeline = socketChannel.pipeline();
        // HttpServerCodec: netty 提供的处理 http 的编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 添加自定义的处理器
        pipeline.addLast("MyHttpServerHandler", new MyHttpServerHandler());

    }

}