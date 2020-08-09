package netty.guigu.netty.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 22:02
 */
public class NettyClientHandler01 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server", CharsetUtil.UTF_8));
    }

    //读取事件时会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端回复的消息" + byteBuf.toString(CharsetUtil.UTF_8)+"-"+ LocalDateTime.now().toString());
        System.out.println("服务器地址为:" + ctx.channel().remoteAddress());
       try {
           int a=1/0;
       }catch (Exception e){

       }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught ");
        cause.printStackTrace();
        ctx.close();
        ctx.channel().close();
        ctx.pipeline().close();
        ctx.pipeline().channel().close();
    }
}