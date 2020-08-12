package netty.guigu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @program: netty-study
 * @description: 群聊服务器
 * @author: HuRan
 * @create: 2020-08-08 10:04
 */
public class GroupCharServer {
    private Selector selector;
    private ServerSocketChannel listenChennel;
    private static final int PORT = 8888;

    public GroupCharServer() {
        try {
            selector = Selector.open();
            listenChennel=ServerSocketChannel.open();
            listenChennel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChennel.configureBlocking(false);
            listenChennel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                final int select = selector.select(1000);
                if (select > 0) {
                    final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            final SocketChannel sc = listenChennel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            final ByteBuffer allocate = ByteBuffer.allocate(1024);
            final int read = socketChannel.read(allocate);
            if(read>0){
                final String msg = new String(allocate.array());
                System.out.println("from 客户端:" + msg);
                //向其他和客户端发送消息
                senInfoToOther( msg,socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + " 离线了");
                //取消注册
                key.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void senInfoToOther(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中");
        for (SelectionKey key : selector.keys()) {
            final Channel channel = key.channel();
            if(channel instanceof SocketChannel && channel!=self){
                SocketChannel dest= (SocketChannel)channel;
                final ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                dest.write(wrap);
            }
        }
    }

    public static void main(String[] args) {
        GroupCharServer server=new GroupCharServer();
        server.listen();
    }
}