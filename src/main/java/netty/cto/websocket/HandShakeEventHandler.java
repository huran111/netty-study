package netty.cto.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-19 22:10
 */
public class HandShakeEventHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete msg = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            System.out.println(msg.requestUri());
            System.out.println(msg.requestHeaders());
            final String userId = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri().split("=")[1];
            //把业务上的id与channel绑定
            ctx.channel().attr(WsContext.USERID).set(userId);
            WsContext.channels.add(ctx.channel());
            final ScheduledFuture<?> scheduledFuture = ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
                ctx.writeAndFlush(new PingWebSocketFrame());
            }, 10, 10, TimeUnit.SECONDS);
            ctx.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    //取消心跳定时任务
                    scheduledFuture.cancel(true);
                }
            });
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}