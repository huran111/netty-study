package netty.guide.demo07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @program: netty-study
 * @description: 编码器
 * @author: HuRan
 * @create: 2020-08-13 14:47
 */
public class MsgPackEncode extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] write = messagePack.write(msg);
        out.writeBytes(write);

    }
}