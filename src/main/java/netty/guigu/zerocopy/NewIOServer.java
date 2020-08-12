package netty.guigu.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 16:34
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address=new InetSocketAddress(9999);
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        final ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(address);

        final ByteBuffer allocate = ByteBuffer.allocate(4096);
        while (true){
            final SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount=0;
            while (-1!=readCount){
                try {
                    readCount=socketChannel.read(allocate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            allocate.rewind();
        }
    }
}