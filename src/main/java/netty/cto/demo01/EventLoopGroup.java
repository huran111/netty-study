package netty.cto.demo01;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-14 23:14
 */
public class EventLoopGroup {
    private EventLoop[] eventLoops=new EventLoop[2];
    private AtomicInteger idx=new AtomicInteger();
    public EventLoopGroup() throws IOException {
        for (int i = 0; i < eventLoops.length; i++) {
            eventLoops[i]=new EventLoop();
        }
    }
    public EventLoop next(){
        //轮询算法
        return eventLoops[idx.getAndIncrement() & eventLoops.length-1];
    }

    public void register(SocketChannel channel,int keyOps) throws IOException {
        next().register(channel,keyOps);
    }

}