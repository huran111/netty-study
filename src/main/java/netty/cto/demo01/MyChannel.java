package netty.cto.demo01;

import netty.cto.demo02.MyHandler1;
import netty.cto.demo02.MyHandler2;
import netty.cto.demo02.PPLine;

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
    private PPLine ppLine;

    public MyChannel(SocketChannel channel, EventLoop eventLoop) {
        this.channel = channel;
        this.eventLoop = eventLoop;
        this.ppLine = new PPLine(this, eventLoop);
        this.ppLine.addLast(new MyHandler1());
        this.ppLine.addLast(new MyHandler2());
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
            this.ppLine.headCtx.fireChannelRead(byteBuffer);
//            byte[] bytes = new byte[read];
//            byteBuffer.get(bytes, 0, read);
//            String clientData = new String(bytes, Charset.forName("utf-8"));
//            System.out.println("clientData:" + clientData);
//            //加入写缓冲区
//            writeQueue.add(ByteBuffer.wrap("hello".getBytes()));
//            if(clientData.equals("flush")){
//                //切换写事件
//                key.interestOps(SelectionKey.OP_WRITE);
//            }

        } catch (Exception e) {
            System.out.println("读取发生异常，关闭socket");
            key.channel();
            socketChannel.close();
        }
    }

    public void write(SelectionKey key) throws IOException {
        ByteBuffer buffer;
        while ((buffer = writeQueue.poll()) != null) {
            channel.write(buffer);
        }
        //切换为读事件感兴趣
        key.interestOps(SelectionKey.OP_READ);
    }

    /**
     * 从尾部开始传递
     * @param msg
     */
    public void doWrite(Object msg) {
        this.ppLine.tailCtx.write(msg);
    }
    public  void addWriteQueue(ByteBuffer buffer){
        writeQueue.add(buffer);
    }

    /**
     * channel 最后开始调用
     */
    public void flush(){
        this.ppLine.tailCtx.flush();
    }
    public void doFlush(){
        this.channel.keyFor(eventLoop.selector).interestOps(SelectionKey.OP_WRITE);
    }

}