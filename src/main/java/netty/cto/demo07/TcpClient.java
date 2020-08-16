package netty.cto.demo07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-16 10:47
 */
public class TcpClient {
    public static void main(String[] args) throws IOException {

    }
    //情况1
    @Test
    public void test01(){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf .writeShort("hello,world".getBytes().length);
        System.out.println("hello,world".getBytes().length);
        byteBuf.writeBytes("hello,world".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
    //情况2
    @Test
    public void test02(){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        //此时+2是为了是消息长度包含头的两个字节 此时要用到lengthAdjustment ：要添加到length字段的值的补偿值
        byteBuf .writeShort("hello,world".getBytes().length+2);
        System.out.println("hello,world".getBytes().length);
        byteBuf.writeBytes("hello,world".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
    //情况3
    @Test
    public void test03(){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf .writeBytes("AB".getBytes());//头2个字节
        byteBuf.writeMedium("hello,world".getBytes().length);//三个字节
        byteBuf.writeBytes("hello,world".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
    //情况4
    @Test
    public void test04(){
        //LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 3, 2, 0));
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeMedium("hello,world".getBytes().length);//三个字节
        byteBuf .writeBytes("AB".getBytes());//头2个字节
        byteBuf.writeBytes("hello,world".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
    //情况5
    @Test
    public void test05(){
        //LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 3, 2, 0));
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf .writeBytes("A".getBytes());//头1个字节
        byteBuf.writeShort("hello,world".getBytes().length);//长度2个字节
        byteBuf .writeBytes("B".getBytes());//头1个字节
        byteBuf.writeBytes("hello,world".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
}