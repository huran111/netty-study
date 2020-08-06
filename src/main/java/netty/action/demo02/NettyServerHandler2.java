package netty.action.demo02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:06
 */
public class NettyServerHandler2 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HelloWorldServerHandler active");
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead...");

        // 读取客户端发送的数据
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("from " + ctx.channel().remoteAddress() + ", " + buf.toString(CharsetUtil.UTF_8));

        System.out.println("当前线程："+Thread.currentThread().getName());
        // 模拟业务处理耗时
       // Thread.sleep(5 * 1000);
       // ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端1\n", CharsetUtil.UTF_8));

        // 用户自定义的任务，任务添加到 taskQueue 中
        ctx.channel().eventLoop().execute(new Runnable() {

            public void run() {
                System.out.println("execute："+Thread.currentThread().getName());
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer(new Date().toLocaleString() + "hello, 客户端1\n", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        // 用户自定义定义任务， 任务添加到 scheduledTaskQueue 中
        ctx.channel().eventLoop().schedule(new Runnable() {
            public void run() {
                try {
                    System.out.println("schedule："+Thread.currentThread().getName());

                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer(new Date().toLocaleString() + "hello, 客户端 shedule\n", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },5 , TimeUnit.SECONDS);
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer(new Date().toLocaleString() + "hello, 客户端2", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}