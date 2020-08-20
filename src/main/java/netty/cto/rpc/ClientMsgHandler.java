package netty.cto.rpc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.cto.demo10.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-17 23:06
 */
public class ClientMsgHandler extends ChannelInboundHandlerAdapter {
    private Channel channel;
    private Map<String, SyncResponse> responseMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        System.out.println(msg.toString());
        MyResponse myResponse = (MyResponse) msg;
        final SyncResponse syncResponse = responseMap.remove(myResponse.getReqId());
        syncResponse.setResult(myResponse.getResult());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        this.channel = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("exceptionCaught");
    }

    public SyncResponse sendRpcRequest(MyRequest myRequest) {
        if (channel == null) {
            throw new RuntimeException("channel已经关闭");
        }
        SyncResponse syncResponse = new SyncResponse(myRequest.getReqId());
        responseMap.put(myRequest.getReqId(), syncResponse);
        channel.writeAndFlush(myRequest);
        return syncResponse;
    }
}