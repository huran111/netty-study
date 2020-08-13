package netty.guide.demo07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @program: netty-study
 * @description: 解码器
 * @author: HuRan
 * @create: 2020-08-13 14:48
 */
public class MsgPackDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int i = in.readableBytes();
        byte[] bytes;
        bytes = new byte[i];
        in.getBytes(in.readerIndex(), bytes, 0, i);
        MessagePack messagePack = new MessagePack();
        out.add(messagePack.read(bytes));
        System.out.println("==========");
    }
}