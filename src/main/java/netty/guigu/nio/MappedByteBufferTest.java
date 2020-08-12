package netty.guigu.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-08 08:52
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        //直接让文件在内存中修改
        RandomAccessFile randomAccessFile=new RandomAccessFile("E:\\code\\netty-study\\src\\main\\1.txt","rw");
        final FileChannel channel = randomAccessFile.getChannel();
        // 0 可以修改的起始位置
        // 5 映射到内存的大小
        final MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte)'B');
        map.put(4,(byte)'9');
        randomAccessFile.close();
    }
}