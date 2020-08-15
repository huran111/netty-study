package netty.cto.demo02;

import netty.cto.demo01.MyChannel;
import netty.cto.demo01.TcpServer;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 13:39
 */
public class HandlerContext {
    private Handler handler;
    private MyChannel myChannel;
    HandlerContext prev;
    HandlerContext next;

    public HandlerContext(Handler handler, MyChannel myChannel) {
        this.handler = handler;
        this.myChannel = myChannel;
    }

    public void fireChannelRead(Object msg) {
        final HandlerContext n = this.next;
        if (n != null) {
            n.handler.channelRead(n, msg);
        }
    }

    /**
     * 找的前一个
     */
    public void flush() {
        final HandlerContext p = this.prev;
        if (p != null) {
            p.handler.flush(p);
        }
    }

    /**
     * 从当前handler开始传递
     *
     * @param msg
     */
    public void write(Object msg) {
        final HandlerContext p = this.prev;
        if (p != null) {
            p.handler.write(p, msg);
        }
    }

    public MyChannel getMyChannel() {
        return myChannel;
    }
}