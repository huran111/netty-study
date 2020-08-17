package netty.guide.demo08;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import java.util.List;
import java.util.Map;

/**
 * @program: netty-study
 * @description: netty解码
 * @author: HuRan
 * @create: 2020-08-14 10:04
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    private MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder(MarshallingEncoder marshallingEncoder) {
        this.marshallingEncoder = marshallingEncoder;
    }



    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        ByteBuf sendBuffer = Unpooled.buffer();
        sendBuffer.writeInt((msg.getHeader().getCrcCode()));
        sendBuffer.writeInt(msg.getHeader().getLength());
        sendBuffer.writeLong(msg.getHeader().getSessionID());
        sendBuffer.writeByte(msg.getHeader().getType());
        sendBuffer.writeByte(msg.getHeader().getPriority());
        sendBuffer.writeInt(msg
                .getHeader().getAttachment().size());
        String key=null;
        byte[] keyArray=null;
        Object value=null;
        for (Map.Entry<String, Object> p: msg.getHeader().getAttachment().entrySet()) {
            key = p.getKey();
            keyArray= key.getBytes("utf-8");
            value=p.getValue();
        }
    }
}