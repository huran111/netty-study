package netty.guigu.netty.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-11 22:17
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端读取" + ctx.channel().remoteAddress() + "，读取到数据:" + msg);
        //发送给客户的一个long
        ctx.writeAndFlush(232323L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}