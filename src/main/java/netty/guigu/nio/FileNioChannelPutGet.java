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
public class FileNioChannelPutGet {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(65);
        byteBuffer.putInt(100);
        byteBuffer.putLong(100L);
        byteBuffer.putChar('a');
        byteBuffer.flip();

        System.out.println();
        System.out.println(byteBuffer.getInt());

        final long aLong = byteBuffer.getLong();
        System.out.println(aLong);
    }
}