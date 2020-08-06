package netty.guide.demo02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-04 15:19
 */
public class ByteBufTest {
    @Test
    public void SliceTest() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1 创建一个 ByteBuf 保存特定字节串。
        ByteBuf slice = buf.slice(0, 14); //.创建从索引 0 开始，并在 14 结束的 ByteBuf 的新 slice。
        System.out.println(slice.toString(utf8));
        buf.setByte(0, 'J'); //更新索引 0 的字节。
        System.out.println(slice.toString(utf8));
        assert buf.getByte(0) == slice.getByte(0); //因为数据是共享的，并以一个地方所做的修改将在其他地方可见
    }

    @Test
    public void CopyingTest() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1
        ByteBuf copy = buf.copy(0, 14); //2
        System.out.println(copy.toString(utf8)); //3
        buf.setByte(0, (byte) 'J'); //4
        //，因为数据不是共享的，并以一个地方所做的修改将不影响其他。
        assert buf.getByte(0) != copy.getByte(0);
    }

    @Test
    public void getAndSet() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1
        System.out.println((char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        //存储当前 readerIndex 和 writerIndex
        System.out.println("readerIndex:" + readerIndex);
        System.out.println("writerIndex:" + writerIndex);
        buf.setByte(0, (byte) 'B'); //4
        System.out.println((char) buf.getByte(0));
        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }
    @Test
    public void readAndWrite() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1
        System.out.println((char)buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        //存储当前 readerIndex 和 writerIndex
        System.out.println("readerIndex:" + readerIndex);
        System.out.println("writerIndex:" + writerIndex);
        buf.writeByte((byte)'?');
        //因为 writeByte() 在  移动了 writerIndex
        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }




}