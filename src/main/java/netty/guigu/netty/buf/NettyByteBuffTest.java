package netty.guigu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 12:12
 */
public class NettyByteBuffTest {
    public static void main(String[] args) {
        final ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < buffer.capacity(); i++) {

            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));

        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());

        }
    }
}