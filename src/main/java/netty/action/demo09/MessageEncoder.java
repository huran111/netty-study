package netty.action.demo09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @program: netty-study
 * @description: 编码器
 * @author: HuRan
 * @create: 2020-08-06 10:47
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageEncoder#encode() 被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}