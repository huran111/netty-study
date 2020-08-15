package netty.cto.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @program: netty-study
 * @description: readSlice 方法
 * @author: HuRan
 * @create: 2020-08-15 16:00
 */
public class ByteBufTest5 {
    public static void main(String[] args) {
        final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(10);
        byteBuf.writeByte(11);
        byteBuf.writeByte(12);
        byteBuf.writeByte(13);
        byteBuf.writeByte(14);
        byteBuf.writeByte(15);
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        // 2 5
        System.out.println(byteBuf.readerIndex() + " " + byteBuf.writerIndex());
        //readSlice 会增加源ByteBuf的 readIndex  增加 byteBuf.readableBytes() 个
        final ByteBuf readSlice = byteBuf.readSlice(byteBuf.readableBytes());
        // 5 5
        System.out.println(byteBuf.readerIndex() + " " + byteBuf.writerIndex());
        // 0 3
        System.out.println(readSlice.readerIndex() + " " + readSlice.writerIndex());

    }
}