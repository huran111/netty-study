package netty.guigu.netty.inbound;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 10:34
 */
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long msg) throws Exception {
        // 客户端读取服务器发送的 long 类型数据
        System.out.println("客户端读取服务器发送的, msg:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端发送 long 类型数据
        // 该处理器的前一个handler是MyLongToByteEncoder
        ctx.writeAndFlush(11212121212121212L);
        //x`ctx.writeAndFlush(Unpooled.copiedBuffer("aaaaaaaaaaaaaaaa", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}