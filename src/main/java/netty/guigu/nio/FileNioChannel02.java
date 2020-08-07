package netty.guigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 21:59
 */
public class FileNioChannel02 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream=new FileInputStream("1.txt");
        final FileChannel channel = fileInputStream.getChannel();
        final FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        final FileChannel channel1 = fileOutputStream.getChannel();
        ByteBuffer  byteBuffer=ByteBuffer.allocate(1024);
        while (true){
            byteBuffer.clear();//清空
            final int read = channel.read(byteBuffer);
            if(read!=-1){
                break;
            }
            byteBuffer.flip();
            channel1.write(byteBuffer);
        }

    }
}