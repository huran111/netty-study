package netty.guigu.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 16:34
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        String fileName = "";
        final FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long startTime = System.currentTimeMillis();

        //windows 调用transferTo 只能传输8m大小，需要分段传输
        // position=fileChannel.size()/8*1024*1024;
        final long l = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的字节数:" + l + ",耗时" + (System.currentTimeMillis() - startTime));
    }
}