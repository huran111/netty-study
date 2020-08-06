package netty.action.demo10;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 11:31
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 10086);
    }
}