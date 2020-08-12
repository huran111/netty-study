package netty.guigu.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 12:32
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        final Channel channel = ctx.channel();
        channels.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("客户:" + channel.remoteAddress());
            }
        });
    }

    //连接建立
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        channels.writeAndFlush("客户端:[{}]" + channel.remoteAddress() + "，加入聊天了\n");
        channels.add(channel);
    }

    //断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        channels.writeAndFlush("客户端：" + channel.remoteAddress() + " 离开了\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
       if(evt instanceof IdleStateEvent){
           IdleStateEvent event= (IdleStateEvent) evt;
           String eventType=null;
           switch (event.state()){
               case READER_IDLE:
                   eventType="读空闲";
                   break;
               case WRITER_IDLE:
                   eventType="写空闲";
                   break;
               case ALL_IDLE:
                   eventType="读写空闲";
                   break;
           }
           System.out.println(ctx.channel().remoteAddress() + "超时:" + eventType);
         //  ctx.channel().close();
       }
    }
}