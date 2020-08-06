package netty.action.demo10;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 11:30
 */
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息，并调用服务
        System.out.println("收到消费方信息，msg: " + msg);
        // 客户端在调用服务的 api，需要定义一个协议
        // 每次发消息都必须以某个字符串开头 "HelloService#Hello#你好"
        String s = msg.toString();
        if (s.startsWith("HelloService#Hello")) {
            TimeUnit.SECONDS.sleep(5);
            String result = new HelloServiceImpl().hello(s.substring(s.lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("NettyServerHandler exception: " + cause.getMessage());
        ctx.close();
    }
}