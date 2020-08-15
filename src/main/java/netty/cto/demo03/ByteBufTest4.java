package netty.cto.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @program: netty-study
 * @description: slice 方法
 * @author: HuRan
 * @create: 2020-08-15 16:00
 */
public class ByteBufTest4 {
    public static void main(String[] args) {
        final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(10);
        byteBuf.writeByte(11);
        byteBuf.writeByte(12);
        byteBuf.writeByte(13);
        byteBuf.writeByte(14);
        byteBuf.writeByte(15);
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readerIndex() + " " + byteBuf.writerIndex());
        final ByteBuf slice = byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes());
        // slice=13 14 15
        System.out.println(slice.readerIndex() + " " + slice.writerIndex());
        System.out.println(slice.readByte());
        // slice.writeByte(555);//抛出异常
        //将slice 下标为0的设置为3 此时源ByteBuf 索引为2 的13 变为了3
        slice.setByte(0, 3);
        System.out.println(byteBuf.readByte());
        //再用源ByteBuf读取索引为2的字节应该为3
        System.out.println(byteBuf.getByte(2));
    }
}