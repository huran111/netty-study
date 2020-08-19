package netty.cto.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.Proxy;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 21:30
 */
public class RpcClientFactory {
    private ClientMsgHandler clientMsgHandler = new ClientMsgHandler();

    public RpcClientFactory(String ip, int port) {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(boosGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("ObjectEncoder",new ObjectEncoder());
                        ch.pipeline().addLast("ObjectDecoder",new ObjectDecoder(Integer.MAX_VALUE
                                , ClassResolvers.weakCachingResolver(null)));
                        ch.pipeline().addLast(clientMsgHandler);

                    }
                });
        try {
            b.connect(ip, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public RpcService getRpcService() {
        return (RpcService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{RpcService.class}, new RpcServiceHandler(clientMsgHandler));
    }
}