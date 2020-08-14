package netty.cto.demo01;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 22:44
 */
public class UnSafeTest {
    public static void main(String[] args) {
        final Unsafe unsafe = getUnsafe();
        final long address = unsafe.allocateMemory(10);
        //传入基础地址，长度10，初始化堆外内存
        unsafe.setMemory(address,10L,(byte) 0);
        unsafe.putByte(address, (byte) 1);
        unsafe.putByte(address+1, (byte) 2);
        unsafe.putByte(address+2, (byte) 3);

        final byte aByte = unsafe.getByte(address);
        System.out.println(aByte);
        final byte aByte1 = unsafe.getByte(address + 1);
        System.out.println(aByte1);
        unsafe.freeMemory(address);
    }
    private static Unsafe getUnsafe(){
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}