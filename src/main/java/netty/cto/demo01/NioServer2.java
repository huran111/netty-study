package netty.cto.demo01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-14 22:08
 */
public class NioServer2 {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8899));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //创建一个事件查询器
        final Selector selector = SelectorProvider.provider().openSelector();
        //把ServerSocketChannel 注册到事件查询器上 并且关注accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //创建一组事件查询器
        EventLoopGroup eventLoopGroup=new EventLoopGroup();

        while (true) {
            final int select = selector.select();
            System.out.println("系统发生IO事件，数量" + select);
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                final SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    final ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    //一个socketchannel代表一个tcp连接
                    final SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("服务器接收了一个新的连接:" + socketChannel.getRemoteAddress());
                    //把socketchannel注册到事件查询器上 并且关注read事件
                    //把它注册到和serverSocketChannel 同一个selector
                    //socketChannel.register(selector,SelectionKey.OP_READ);
                    //把它注册到一个新的selector 读事件
                    eventLoopGroup.register(socketChannel, SelectionKey.OP_READ);
                }

            }
        }
    }
}