package netty.guigu.nio;

import java.nio.ByteBuffer;

/**
 * @program: netty-study
 * @description: 只读buffer
 * @author: HuRan
 * @create: 2020-08-06 22:27
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {

        ByteBuffer byteBuffer=ByteBuffer.allocate(45);
        for (int i = 0; i < 45; i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();
        //返回只读buffer
        final ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        System.out.println(byteBuffer1.getClass());
        while (byteBuffer1.hasRemaining()){
            System.out.println(byteBuffer1.get());
        }
    }
}