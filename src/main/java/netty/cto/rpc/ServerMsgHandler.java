package netty.cto.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.cto.demo10.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-17 23:05
 */
public class ServerMsgHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ServerMsgHandler() {
        beanMap.put(RpcService.class.getName(), new RpcServiceImpl());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
        MyRequest myRequest = (MyRequest) msg;
        executorService.submit(() -> {
            Object invoke = null;
            try {
                final Object tarGet = beanMap.get(myRequest.getClassName());
                final Method method = tarGet.getClass().getMethod(myRequest.getMethodName(), myRequest.getArgsCls());

                invoke = method.invoke(tarGet, myRequest.getArgs());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            MyResponse myResponse = new MyResponse();
            myResponse.setReqId(myRequest.getReqId());
            myResponse.setResult(invoke);
            System.out.println("serverserverserverserverserver");
            //ctx.writeAndFlush(myResponse);
            ctx.channel().writeAndFlush(myResponse);
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("====");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}