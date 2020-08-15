package netty.cto.demo05;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 18:00
 */
public class OutboundHandlerA extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(" write  OutboundHandlerA ---被调用");
        super.write(ctx, msg, promise);
    }
}