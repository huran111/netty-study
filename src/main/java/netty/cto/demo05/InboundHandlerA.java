package netty.cto.demo05;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 17:58
 */
public class InboundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("  channelRead  InboundHandlerA------被调用");
        super.channelRead(ctx, msg);
    }
}