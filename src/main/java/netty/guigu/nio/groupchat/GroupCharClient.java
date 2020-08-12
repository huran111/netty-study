package netty.guigu.nio.groupchat;

import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 10:25
 */
public class GroupCharClient {
    private Selector selector;
    private SocketChannel socketChannel;
    private static final int PORT = 8888;
    private static final String HOST = "127.0.0.1";
    private String username;
    public GroupCharClient() throws IOException {
        selector=Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_READ);
        username=socketChannel.getLocalAddress().toString().substring(1);
    }
    public void  sendInfo(String info){
        info=username+ "说"+info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void  readInfo() throws IOException {
        final int select = selector.select();
        if(select>0){
            final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                final SelectionKey key = iterator.next();
                if(key.isReadable()){
                    final SocketChannel sc = (SocketChannel) key.channel();
                    final ByteBuffer allocate = ByteBuffer.allocate(1024);
                    sc.read(allocate);
                    final String string;
                    string = new String(allocate.array());
                    System.out.println(string.trim());
                }
            }
            //防止重复操作
            iterator.remove();
        }else {
           // System.out.println("没有可用通道");

        }
    }
    public static void main(String[] args) throws IOException {
        GroupCharClient charClient=new GroupCharClient();
        new Thread(()->{
                while (true){
                    try {
                        charClient.readInfo();
                        TimeUnit.SECONDS.sleep(3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        }).start();
        final Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            final String s = scanner.nextLine();
            charClient.sendInfo(s);
        }
    }
}