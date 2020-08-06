package netty.guide.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-03 15:10
 */
public class TimeServerhandler extends ChannelInboundHandlerAdapter {
    private int counter;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuffer = (ByteBuf) msg;
        int i = byteBuffer.readableBytes();
        byte[] req = new byte[i];
        byteBuffer.readBytes(req);
        String body = new java.lang.String(req, "UTF-8");
        System.out.println("time server receive order:" + body);
        java.lang.String currentTime = "query time order".equals(body) ? new Date(System.currentTimeMillis()).toString() : "bad order";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}