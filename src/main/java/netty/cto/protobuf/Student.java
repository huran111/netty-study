package netty.cto.protobuf;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;

import java.io.Serializable;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-20 14:26
 */
public class Student implements Serializable {
    private String userName;
    private String password;
    private String email;
    private int age;
    private long timespace;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTimespace() {
        return timespace;
    }

    public void setTimespace(long timespace) {
        this.timespace = timespace;
    }

    public static void main(String[] args) {
    }


}