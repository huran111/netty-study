package netty.guigu.netty.tcp;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-12 22:20
 */
public class MessageProto {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }


    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}