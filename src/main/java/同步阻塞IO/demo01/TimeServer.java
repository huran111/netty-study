package 同步阻塞IO.demo01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-03 13:20
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 9999;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the time is start in port:" + port);
            Socket socket = null;
            while (true){
                 socket = server.accept();
                new Thread(new TimeServerhandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(server!=null){
                System.out.println("the time server close");
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server=null;
            }
        }
    }
}