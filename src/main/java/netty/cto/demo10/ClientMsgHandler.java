package netty.cto.demo10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-17 23:06
 */
public class ClientMsgHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
        User user= (User) msg;
        System.out.println(user.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        User user = new User();
        user.setAge(23);
        user.setName("A");
        ctx.channel().writeAndFlush(user);
    }
}