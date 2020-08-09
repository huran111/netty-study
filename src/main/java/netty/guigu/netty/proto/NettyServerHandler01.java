package netty.guigu.netty.proto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 21:53
 */
public class NettyServerHandler01 extends ChannelInboundHandlerAdapter {
    //读取数据
    //ChannelHandlerContext 上下文对象 包含管道
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StudentProto.Student student= (StudentProto.Student) msg;
        System.out.println("客户端发送的数据:" + student.getName());
        System.out.println("客户端发送的数据:" + student.getId());

    }

    /**
     * 读取数据完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵喵1.".getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        ctx.channel().close();
    }
}