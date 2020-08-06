package netty.action.demo10;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 11:32
 */
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;// 返回的结果
    private String para;// 客户端调用方法时，传入的参数

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx; // 其他方法中要用到 ChannelHandlerContext
    }

    /**
     * 收到服务器返回后调用该方法
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        System.out.println("NettyClientHandler#channelRead() 被调用， result=" + result);
        notify(); // 唤醒等待的线程
    }

    /**
     * 被代理对象调用，发送数据给服务器，wait，等待被 channelRead 唤醒
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("para:" + para);
        context.writeAndFlush(para);
        wait(); // 等待 channelRead 方法获取服务器返回结果后唤醒
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public void setPara(String para) {
        this.para = para;
    }
}