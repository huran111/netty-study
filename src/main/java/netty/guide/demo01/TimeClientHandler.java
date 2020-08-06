package netty.guide.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-03 15:20
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req;
    private int counter;
    public TimeClientHandler() {
        req=("query time order"+System.getProperty("line.separator")).getBytes();
    }




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;
        for (int i = 0; i < 100; i++) {
            message=Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf= (ByteBuf) msg;
        int i = byteBuf.readableBytes();
        byte[] req=new byte[i];
        String boyd=new String(req,"utf-8");
        System.out.println(boyd+":"+(++counter));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}