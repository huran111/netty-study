package netty.guigu.netty.socket;

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

import java.util.concurrent.ExecutionException;
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
        //1 比如这个这里有一个比较耗时的业务
// -------------模拟耗时操作
//        TimeUnit.SECONDS.sleep(10);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵喵2.".getBytes()));
//------------------用户自定会任务 异步执行  提交改channel的NioEventLoop的taskqueue中去
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵喵2.".getBytes()));
//            }
//        });
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵喵3.".getBytes()));
//            }
//        });

        // 用户提交定时任务
        final ScheduledFuture<?> schedule = ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
                final ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵喵4.".getBytes()));
                final ChannelFuture future1 = future.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("isDone:"+future.isDone());
                        System.out.println("isSuccess:"+future.isSuccess());
                    }
                });
//                try {
//                    System.out.println("get start");
//                 //   future1.get();
//                    System.out.println("get end");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
            }
        }, 10, TimeUnit.SECONDS);
        System.out.println("server ctx=" + ctx);
        System.out.println("服务端读取线程:" + Thread.currentThread().getName());
        final ChannelPipeline pipeline = ctx.pipeline();
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址" + ctx.channel().remoteAddress());
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