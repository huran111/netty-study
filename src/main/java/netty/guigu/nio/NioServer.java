package netty.guigu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 09:32
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个selector对象
        final Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serversocketchannel注册到selector  关心事件为op_acceot
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //连接事件
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒");
                continue;
            }
            //有事件发生
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("有事件发生的：" + selectionKeys.size());
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                final SelectionKey key = iterator.next();
                //有新的客户端连接我
                if(key.isAcceptable()){
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    //注意 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成，生成了一个socketchannel" + socketChannel.hashCode());
                    //关注事件为read 并关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){
                    final SocketChannel channel = (SocketChannel) key.channel();
                    //获取到关联的buffer
                    final ByteBuffer attachment = (ByteBuffer) key.attachment();
                    channel.read(attachment);
                    System.out.println("from 客户端" + new String(attachment.array()));
                }
                //从集合中移除当前的selectorkey
                iterator.remove();
            }
        }
    }
}