package netty.action.demo10;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 11:31
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    // 创建线程池
    private static ExecutorService excutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;

    // 使用代理模式，获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String provider) {

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass},
                (proxy, method, args) -> {
                    if (client == null) {
                        System.out.println("client == null");
                        initClient();
                    }
                    // 设置要发给服务器的消息
                    System.out.println("client != null");
                    client.setPara(provider + args[0]);
                    return excutor.submit(client).get();
                });
    }

    // 初始化客户端
    private static void initClient() {
        client = new NettyClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });


        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 10086).sync();
            //future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        future.addListener(new ChannelFutureListener() {
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                if (future.isDone()) {
//                    System.out.println("连接服务器端口 10086 成功");
//                } else {
//                    System.out.println("连接服务器端口 10086 失败");
//                }
//            }
//        });

//        future.channel().closeFuture().sync();
    }
}