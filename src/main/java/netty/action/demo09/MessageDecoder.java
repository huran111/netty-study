package netty.action.demo09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @program: netty-study
 * @description: 解码器
 * @author: HuRan
 * @create: 2020-08-06 10:51
 */
public class MessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder#decode() 被调用");
        // 将得到的二进制字节码 =》 MessageProtocol 对象
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        // 封装成 MessageProtoCol 对象，传递给下一个 handler
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}