package netty.cto.demo02;


import java.nio.ByteBuffer;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 13:58
 */
public class MyHandler1 implements Handler {
    @Override
    public void channelRead(HandlerContext ctx, Object msg) {
        System.out.println("  MyHandler1 channelRead:" + msg);
        ByteBuffer buffer= (ByteBuffer) msg;
        final int limit = buffer.limit();
        byte[] content=new byte[limit];
        buffer.get(content);
        final String str = new String(content);
        ctx.fireChannelRead(str);
        buffer.clear();
    }

    @Override
    public void write(HandlerContext ctx, Object msg) {
        System.out.println("MyHadnler1 write   ==" + msg);
        final ByteBuffer wrap = ByteBuffer.wrap(msg.toString().getBytes());
       // ctx.getMyChannel().doWrite(msg);
        ctx.write(wrap);
    }

    @Override
    public void flush(HandlerContext ctx) {
        System.out.println("MyHandler1 flush:" + ctx.toString());
        ctx.flush();
    }
}