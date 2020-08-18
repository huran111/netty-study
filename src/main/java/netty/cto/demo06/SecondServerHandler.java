package netty.cto.demo06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 16:39
 */
public class SecondServerHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("SecondServerHandler:" + msg.toString());
        ctx.channel().writeAndFlush("client hello");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        cause.printStackTrace();
        ctx.close();
    }

}