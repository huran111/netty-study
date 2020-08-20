package netty.cto.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 21:37
 */
public class RpcClient {
    public static void main(String[] args) {
        RpcClientFactory clientFactory = new RpcClientFactory("localhost", 8888);
        final RpcService rpcService = clientFactory.getRpcService();
        System.out.println(rpcService.rpcLogin("admin", "123456"));

    }
}