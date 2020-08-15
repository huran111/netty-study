package netty.cto.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @program: netty-study
 * @description: duplicate 方法
 * @author: HuRan
 * @create: 2020-08-15 16:00
 */
public class ByteBufTest2 {
    public static void main(String[] args) {
        final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(10);
        byteBuf.writeByte(11);
        byteBuf.writeByte(12);
        byteBuf.writeByte(13);
        byteBuf.writeByte(14);
        byteBuf.writeByte(15);
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readerIndex()+" "+byteBuf.writerIndex());
        //该方法索引是独立维护，缓冲区是共享的 所以会覆盖 77->99
        final ByteBuf duplicate = byteBuf.duplicate();
        byteBuf.writeByte(99);
        System.out.println(byteBuf.readerIndex()+" "+byteBuf.writerIndex());
        duplicate.writeByte(77);
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());

    }
}