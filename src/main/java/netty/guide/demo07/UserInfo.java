package netty.guide.demo07;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 11:26
 */
@Message
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private String username;
    private int userID;

    public UserInfo buildUserName(String userName) {
        this.username = userName;
        return this;
    }

    public UserInfo buildUserId(int userID) {
        this.userID = userID;
        return this;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] codeC() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] value = this.username.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.userID);
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }
}