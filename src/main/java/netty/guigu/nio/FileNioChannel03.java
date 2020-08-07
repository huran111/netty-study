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
public class FileNioChannel03 {
    public static void main(String[] args) throws IOException {
       FileInputStream fileInputStream=new FileInputStream("");
       FileOutputStream fileOutputStream=new FileOutputStream("");
        final FileChannel sourceChannel = fileInputStream.getChannel();
        final FileChannel destChannel = fileOutputStream.getChannel();
        destChannel.transferFrom(sourceChannel,0,sourceChannel.size());
        sourceChannel.close();
        destChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}