package netty.guigu.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import netty.guigu.netty.proto2.MyDataInfo;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 22:02
 */
public class NettyClientHandler03 extends SimpleChannelInboundHandler<MessageProto> {
    int count=0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProto msg) throws Exception {
        final int len = msg.getLen();
        final byte[] content = msg.getContent();
        System.out.println("客户端接收到的消息如下:");
        System.out.println("长度:" + len);
        System.out.println("内容:" + new String(content,Charset.forName("utf-8")));
        System.out.println(this.count++);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i <1; i++) {
            String msg="大家好，我叫胡冉.";
            final byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
            final int length = msg.getBytes(Charset.forName("utf-8")).length;
            final MessageProto messageProto = new MessageProto();
            messageProto.setContent(bytes);
            messageProto.setLen(length);
            ctx.writeAndFlush(messageProto);
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