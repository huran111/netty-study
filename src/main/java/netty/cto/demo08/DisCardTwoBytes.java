package netty.cto.demo08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-16 22:20
 */
public class DisCardTwoBytes extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        final ByteBuf newBuf = byteBuf.readRetainedSlice(byteBuf.readableBytes()-2);
        ctx.fireChannelRead(newBuf);
        byteBuf.release();
    }
}