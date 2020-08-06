package netty.action.demo08;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 10:29
 */
public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        /* 向管道加入处理器 */
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 入站的 handler 进行解码
        pipeline.addLast("decoder", new MyByteToLongDecoder());
        // 添加一个出站的 handler 对数据进行编码
        pipeline.addLast("encoder", new MyLongToByteEncoder());

        // 添加自定义的处理器
        pipeline.addLast("MyServerHandler", new MyServerHandler());
    }
}