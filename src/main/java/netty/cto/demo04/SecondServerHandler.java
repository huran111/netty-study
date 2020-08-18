package netty.cto.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
        ctx.channel().write(msg);
        if (msg.equals("flush")) {
            ctx.channel().flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        final ByteBuf buffer = ctx.alloc().buffer(32);
        String str = (String) msg;
        buffer.writeBytes(str.getBytes("utf-8"));
        ctx.write(buffer);
    }
}