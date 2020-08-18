package netty.cto.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 16:39
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        System.out.println("DiscardServerHandler=========");
        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            final String str = byteBuf.toString(Charset.forName("utf-8"));
            //传递给下一个context
          ctx.fireChannelRead(str);

        } finally {
            System.out.println("release");
            byteBuf.release();
            // ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}