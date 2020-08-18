package netty.cto.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
/**
 * @program: netty-study
 * @description: retainedDuplicate 方法
 * @author: HuRan
 * @create: 2020-08-15 16:00
 */
public class ByteBufTest3 {
    public static void main(String[] args) {
        final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(10);
//        final ByteBuf duplicate = byteBuf.duplicate();
//        System.out.println("byteBuf:"+byteBuf.refCnt());
//        System.out.println("duplicate:"+duplicate.refCnt());
//        //以上 duplicate 方法返回的新的ByteBuf 和源ByteBuf  引用计数器都会变为0
//        //但是他们各自维护各自的索引，会有覆盖数据的情况
//        byteBuf.release();
//        System.out.println("byteBuf:"+byteBuf.refCnt());
//        System.out.println("duplicate:"+duplicate.refCnt());
        System.out.println("=====================================");
        //retainedDuplicate 方法会增加引用计数器
        final ByteBuf duplicate = byteBuf.retainedDuplicate();
        System.out.println("byteBuf:" + byteBuf.refCnt()); //2
        System.out.println("duplicate:" + duplicate.refCnt()); //1
        duplicate.release();
        byteBuf.release();
        System.out.println("byteBuf:" + byteBuf.refCnt());
        System.out.println("duplicate:" + duplicate.refCnt());
    }
}