package netty.guigu.nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * @program: netty-study
 * @description: 缓冲区
 * @author: HuRan
 * @create: 2020-08-06 21:44
 */
public class BioBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer=IntBuffer.allocate(5);
        for(int i=0;i<intBuffer.capacity();i++){
            intBuffer.put(i);
        }

        System.out.println("limit:"+intBuffer.limit());
        System.out.println("position:"+intBuffer.position());
         intBuffer.flip();
        System.out.println("position:"+intBuffer.position());
        System.out.println("limit:"+intBuffer.limit());
       // intBuffer.position(2);//指定位置读取
       //    intBuffer.limit(3);//指定位置读取
        while (intBuffer.hasRemaining()){
            final int i = intBuffer.get();
            System.out.println(i);
        }
    }
}