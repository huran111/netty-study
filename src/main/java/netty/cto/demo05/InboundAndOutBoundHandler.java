package netty.cto.demo05;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 18:01
 */
public class InboundAndOutBoundHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" channelRead  InboundAndOutBoundHandler ----被调用");
        super.channelRead(ctx, msg);
        //ctx.channel() 会从尾部调用 netty会自动释放资源 如果是ctx.writeAndFlush则需要自己释放 不经过尾部的话
        ctx.channel().writeAndFlush(ctx.alloc().buffer(16).writeBytes("hello".getBytes()));
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(" write InboundAndOutBoundHandler ----被调用");
        super.write(ctx, msg, promise);
    }
}