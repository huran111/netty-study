package netty.action.demo03;

/**
 * @program: netty-in-action
 * @description:
 * @author: HuRan
 * @create: 2020-08-05 22:17
 */
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("===================================");
        System.out.println("msg 类型： " + msg.getClass().getName());
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());

        // 判断 msg 是否是 http request 请求
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            System.out.println("请求 uri: " + uri.getPath());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求 favicon.icon，不做响应");
                return;
            }

            // 回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
            // 构造一个 http 的响应，即 http response
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
        }
    }

}