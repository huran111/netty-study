package netty.guide.demo07;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 10:52
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" client channelActive");
        UserInfo[] userInfos = UserInfo();
        for (UserInfo userInfo : userInfos) {
            ctx.writeAndFlush(userInfo);
        }

    }

    private UserInfo[] UserInfo() {
        UserInfo[] userInfos = new UserInfo[5];
        UserInfo userInfo = null;
        for (int i = 0; i < 5; i++) {
            userInfo = new UserInfo();
            userInfo.setUserID(i);
            userInfo.setUsername("胡冉" + i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive msgpack message " + msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}