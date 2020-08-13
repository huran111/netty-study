package netty.guide.demo07;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 11:40
 */
public class TestUserInfo {
    public static void main(String[] args) {
        UserInfo userInfo=new UserInfo();
        userInfo.buildUserName("胡冉").buildUserId(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            int length = bytes.length;
            System.out.println("jdk序列化的长度为:" + length);
            int length1 = userInfo.codeC().length;
            System.out.println("字节序列化长度为:" + length1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}