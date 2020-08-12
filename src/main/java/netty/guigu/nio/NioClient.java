package netty.guigu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 09:44
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        final InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接有时间，客户端不会阻塞，可以做其他工作");
            }
        }
        //如果连接成功 发送数据
        String str = "hello";
        final ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(wrap);
        System.in.read();

    }
}