package netty.cto.websocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-19 22:25
 */
public class WsContext {
    public static final ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final AttributeKey USERID=AttributeKey.valueOf("USERID");
}