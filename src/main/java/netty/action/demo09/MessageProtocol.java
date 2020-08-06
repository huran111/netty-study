package netty.action.demo09;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 10:46
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getLen() {
        return len;
    }
}