package netty.guigu.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: netty-study
 * @description: BIO服务器
 * @author: HuRan
 * @create: 2020-08-06 21:33
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket=new ServerSocket(6666);
        System.out.println("服务端启动了");
        while (true){
            System.out.println("等待连接");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                   handler(socket);
                }


            });
        }
    }
    public static void  handler(Socket socket){
        byte[] bytes=new byte[1024];
        try {
            final InputStream inputStream = socket.getInputStream();
            while (true){
                System.out.println("线程ID:"+Thread.currentThread().getName());
                final int read = inputStream.read(bytes);
                if(read!=-1){
                    System.out.println(new String(bytes, 0, read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}