package netty.guigu.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 16:02
 */
public class OldIoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2222);
        String fileName = "E:\\test.avi";
        final FileInputStream fileInputStream = new FileInputStream(fileName);
        final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long startTime=System.currentTimeMillis();
        while ((readCount = fileInputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数:" + total + ",耗时:" + (System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        socket.close();
        fileInputStream.close();
    }
}