package netty.shengsiyuan;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 14:25
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<SocketChannel> {


    /**
     * 连接后调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded:" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved:" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught:" + ctx.channel().id().asLongText());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SocketChannel msg) throws Exception {

    }
}