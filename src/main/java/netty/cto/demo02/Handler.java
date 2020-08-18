package netty.cto.demo02;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-15 13:39
 */
public interface Handler {
    public void channelRead(HandlerContext ctx,Object msg);
    void write(HandlerContext ctx,Object msg);
    void  flush(HandlerContext ctx);
}