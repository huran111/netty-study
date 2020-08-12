package netty.guigu.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 16:08
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(2222);
        while (true){
            final Socket socket = serverSocket.accept();
            final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes=new byte[4096];
            while (true){
                final int read = dataInputStream.read(bytes, 0, bytes.length);
                if(-1==read){
                    break;
                }
            }
        }
    }
}