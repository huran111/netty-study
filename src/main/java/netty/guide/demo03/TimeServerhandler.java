package netty.guide.demo03;

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
        String body = new String(req, "UTF-8").substring(0,req.length-System.getProperty("line.separator").length());
        System.out.println("the time serve receive order " + body + "; the counter is :" + (++counter));
        String currentTime = "query time order".equals(body) ? new Date(System.currentTimeMillis()).toString() : "bad order";
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