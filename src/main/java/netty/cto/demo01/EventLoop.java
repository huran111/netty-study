package netty.cto.demo01;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-14 22:49
 */
public class EventLoop implements Runnable {
    public Selector selector;
    private Thread thread;
    private Queue<Runnable> taskQueue = new LinkedBlockingDeque<>();

    public EventLoop() throws IOException {
        this.selector = SelectorProvider.provider().openSelector();
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * 把channel 注册到事件查询器上
     *
     * @param channel
     * @param keyOps
     * @throws ClosedChannelException
     */
    public void register(SocketChannel channel, int keyOps) throws IOException {
        taskQueue.add(() -> {
            //封装一个runnable
            try {
                final SelectionKey selectionKey = channel.register(selector, keyOps);
                MyChannel myChannel = new MyChannel(channel, this);
                selectionKey.attach(myChannel);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        });
        //唤起selector上阻塞的线程
        selector.wakeup();


    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                System.out.println(thread + " 开始查询IO事件");
                final int num = selector.select();
                System.out.println("系统发生IO事件，数量" + num);
                if (num > 0) {
                    final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey key = iterator.next();
                        MyChannel myChannel = (MyChannel) key.attachment();
                        myChannel = (MyChannel) key.attachment();
                        iterator.remove();
                        if (key.isReadable()) {
                            //读事件
                            myChannel.read(key);
                            break;
                        }
                        //写事件
                        if (key.isWritable()) {
                            myChannel.write(key);
                            break;
                        }
                    }
                }
                Runnable task;
                while ((task = taskQueue.poll()) != null) {
                    task.run();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}