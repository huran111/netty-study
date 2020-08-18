package netty.guigu.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @program: netty-study
 * @description: 解码
 * @author: HuRan
 * @create: 2020-08-12 22:26
 */
public class MyMessageDecode extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecode 被调用");
        final int i = in.readInt();
        final byte[] bytes = new byte[i];
        in.readBytes(bytes);
        MessageProto messageProto=new MessageProto();
        messageProto.setLen(i);
        messageProto.setContent(bytes);
        out.add(messageProto);
    }
}