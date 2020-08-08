package netty.guigu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 09:02
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        //Scattering 数据写入channel时，可以 采用buffer数组，依次写入
        // Gathering 从buffer读取数据时，可以采用buffer数组 依次读
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(3333);
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer[] byteBuffer=new ByteBuffer[2];
        byteBuffer[0]=ByteBuffer.allocate(5);
        byteBuffer[1]=ByteBuffer.allocate(3);
        final SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength=8;
        while (true){
            int byteRead=0;
            while (byteRead<messageLength){
                final long read = socketChannel.read(byteBuffer);
                byteRead+=read;
                System.out.println("byteRead:" + byteRead);
                //当前的buffer的位置
                Arrays.asList(byteBuffer).stream().map(buffer->"position="+buffer.position()+", limit="+buffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer进行反转
            Arrays.asList(byteBuffer).forEach(buffer->buffer.flip());
            //将数据读出 显示到客户端
            long  byteWirte=0;
            while (byteWirte<messageLength){
                final long write = socketChannel.write(byteBuffer);
                byteWirte+=write;
            }
            //将所有的buffer 进行clear
            Arrays.asList(byteBuffer).forEach(buffer->buffer.clear());
            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWirte);
        }
    }
}