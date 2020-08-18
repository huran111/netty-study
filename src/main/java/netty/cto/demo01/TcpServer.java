package netty.cto.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 23:04
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3333);
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("客户端连接成功:" + socket.getRemoteSocketAddress());
            new Thread(new Handler(socket)).start();
        }
    }

    public static class Handler implements Runnable {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            int readnum = 0;
            byte[] content = new byte[1024];
            try {
                final InputStream inputStream = socket.getInputStream();
                final OutputStream outputStream = socket.getOutputStream();
                while ((readnum = inputStream.read(content)) != -1) {
                    final String s = new String(Arrays.copyOf(content, readnum));
                    System.out.println(s);
                    outputStream.write("hello client".getBytes());
                }
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}