package netty.guide.demo07;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 14:30
 */
public class PerformTestUserInfo {
    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserId(100).buildUserName("胡冉");
        int loop = 1000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {

            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            System.out.println(b.length);
            bos.close();
        }
        System.out.println(System.currentTimeMillis() - startTime + " ms");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] bytes = info.codeC();
        }
        System.out.println(System.currentTimeMillis() - startTime + " ms");
    }
}