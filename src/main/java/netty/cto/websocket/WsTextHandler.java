package netty.cto.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelMatcher;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-19 20:53
 */
public class WsTextHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
       WsContext.channels.writeAndFlush(new TextWebSocketFrame("hello client"), new ChannelMatcher() {
           @Override
           public boolean matches(Channel channel) {
               return false;
           }
       });

    }
}