package netty.cto.demo02;

import java.nio.ByteBuffer;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 13:58
 */
public class MyHandler2 implements Handler {
    @Override
    public void channelRead(HandlerContext ctx, Object msg) {
        System.out.println("  MyHandler2 channelRead:" + msg);
        String str= (String) msg;
        System.out.println( "222 channelRead:"+str);
        ctx.getMyChannel().doWrite("hello client");
        if(str.equals("flush")){
            ctx.flush();
        }
    }

    @Override
    public void write(HandlerContext ctx, Object msg) {
        System.out.println("MyHandler2 write    ==" + msg);
        final ByteBuffer wrap = ByteBuffer.wrap(msg.toString().getBytes());
        // ctx.getMyChannel().doWrite(msg);
        ctx.write(wrap);
    }

    @Override
    public void flush(HandlerContext ctx) {
        System.out.println("MyHandler2 flush:" + ctx.toString());
        ctx.getMyChannel().flush();
    }
}