package netty.guide.demo03;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-03 15:01
 */
public class ChildChannelHandler extends ChannelInitializer <SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new TimeServerhandler());
    }
}