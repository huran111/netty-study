package netty.guigu.netty.tcp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 21:53
 */
public class NettyServerHandler03 extends SimpleChannelInboundHandler<MessageProto> {

    int count=0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProto msg) throws Exception {
        final int len = msg.getLen();
        final byte[] content = msg.getContent();
        System.out.println("服务器收到信息如下");
        System.out.println("长度:"+len);
        final String s = new String(content, Charset.forName("utf-8"));
        System.out.println("内容:"+s);
        System.out.println(this.count++);
        //回复客户端消息
        final String string = UUID.randomUUID().toString();
        final byte[] stringBytes = string.getBytes("utf-8");
        final int length = string.getBytes("utf-8").length;
        MessageProto messageProto=new MessageProto();
        messageProto.setLen(length);
        messageProto.setContent(stringBytes);
        ctx.writeAndFlush(messageProto);
    }

    /**
     * 读取数据完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        ctx.channel().close();
    }
}