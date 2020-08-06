package 同步阻塞IO.demo02;

import 同步阻塞IO.demo01.TimeServerhandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-03 13:20
 */
public class TimeServer02 {
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
            TimeServerHandlerExecutePool pool=new TimeServerHandlerExecutePool(2,2);;

            while (true){
                 socket = server.accept();
                pool.execute(new TimeServerhandler(socket));
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