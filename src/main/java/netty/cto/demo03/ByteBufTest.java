package netty.cto.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @program: netty-study
 * @description: retain  release方法
 * @author: HuRan
 * @create: 2020-08-15 16:00
 */
public class ByteBufTest {
    public static void main(String[] args) {
        final ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(10);
        System.out.println(byteBuf.refCnt());
        //引用计数器加1
        byteBuf.retain();
        //引用计数器减1
        byteBuf.release();
        System.out.println(byteBuf.refCnt());
    }
}