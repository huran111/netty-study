package netty.guigu.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 21:59
 */
public class FileNioChannel01 {
    public static void main(String[] args) {
        String str="her";
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream("d:\\file01.txt");
            final FileChannel channel = fileOutputStream.getChannel();
            final ByteBuffer allocate = ByteBuffer.allocate(1024);
            allocate.put(str.getBytes());
            allocate.flip();
            channel.write(allocate);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}