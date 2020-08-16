package netty.cto.demo06;

import io.netty.buffer.ByteBufUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-16 10:47
 */
public class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",8080);
        StringBuilder sb=new StringBuilder();
//        for (int i = 0; i < 20000; i++) {
//            sb.append("1");
//        }
        String json="AAAA\r\n";
        String json2="BBBB\r\n";
        System.out.println(ByteBufUtil.hexDump(json.getBytes()));
        System.out.println(ByteBufUtil.hexDump(json2.getBytes()));
        socket.getOutputStream().write(json.getBytes());
        socket.getOutputStream().write(json2.getBytes());
        socket.close();
    }
}