package netty.cto.demo02;

import netty.cto.demo01.EventLoop;
import netty.cto.demo01.MyChannel;

import java.nio.ByteBuffer;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 13:47
 */
public class PPLine {
    public MyChannel myChannel;
    public EventLoop eventLoop;
    public HandlerContext tailCtx;
    public HandlerContext headCtx;

    public PPLine(MyChannel myChannel, EventLoop eventLoop) {
        this.myChannel = myChannel;
        this.eventLoop = eventLoop;
        PPHandler ppHandler = new PPHandler();
        this.headCtx = new HandlerContext(ppHandler, myChannel);
        this.tailCtx = new HandlerContext(ppHandler, myChannel);
        //构建了一个链表
        this.headCtx.next = this.tailCtx;
        this.tailCtx.prev = this.headCtx;
    }

    public void addLast(Handler handler) {
        HandlerContext context = new HandlerContext(handler, myChannel);
        final HandlerContext p = this.tailCtx.prev;
        p.next = context;
        context.prev = p;
        context.next = this.tailCtx;
        this.tailCtx.prev = context;
    }

    public class PPHandler implements Handler {

        @Override
        public void channelRead(HandlerContext ctx, Object msg) {
            System.out.println("PPHandler channelRead:" + msg);
        }

        @Override
        public void write(HandlerContext ctx, Object msg) {
            System.out.println("PPHandler write   ==   " + msg);
            if (!(msg instanceof ByteBuffer)) {
                throw new RuntimeException("类型不对:" + msg.getClass());
            }
            PPLine.this.myChannel.addWriteQueue((ByteBuffer) msg);
        }

        @Override
        public void flush(HandlerContext ctx) {
            System.out.println("PPHandler flush:" + ctx.toString());
            //切换写事件
            myChannel.doFlush();
        }
    }
}