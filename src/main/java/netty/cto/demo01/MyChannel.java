package netty.cto.demo01;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-14 23:27
 */
public class MyChannel {
    private SocketChannel channel;
    private EventLoop eventLoop;
    private Queue<ByteBuffer> writeQueue = new LinkedBlockingDeque<>();

    public MyChannel(SocketChannel channel, EventLoop eventLoop) {
        this.channel = channel;
        this.eventLoop = eventLoop;
    }

    public void read(SelectionKey key) throws IOException {
        final SocketChannel socketChannel = (SocketChannel) key.channel();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            final int read = socketChannel.read(byteBuffer);
            if (read == -1) {
                System.out.println("表示关闭socket");
                key.channel();
                socketChannel.close();
                return;
            }
            byteBuffer.flip();
            byte[] bytes = new byte[read];
            byteBuffer.get(bytes, 0, read);
            String clientData = new String(bytes, Charset.forName("utf-8"));
            System.out.println("clientData:" + clientData);
            //加入写缓冲区
            writeQueue.add(ByteBuffer.wrap("hello".getBytes()));
            if(clientData.equals("flush")){
                //切换写事件
                key.interestOps(SelectionKey.OP_WRITE);
            }

        } catch (Exception e) {
            System.out.println("读取发生异常，关闭socket");
            key.channel();
            socketChannel.close();
        }
    }

    public void write(SelectionKey key) throws IOException {
        ByteBuffer buffer;
        while ((buffer=writeQueue.poll())!=null){
            channel.write(buffer);
        }
        //切换为读事件感兴趣
        key.interestOps(SelectionKey.OP_READ);
    }
}