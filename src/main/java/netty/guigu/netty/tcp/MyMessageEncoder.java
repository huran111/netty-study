package netty.guigu.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @program: netty-study
 * @description: 编码
 * @author: HuRan
 * @create: 2020-08-12 22:24
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProto msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncoder 被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}