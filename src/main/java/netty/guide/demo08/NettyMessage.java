package netty.guide.demo08;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-14 09:53
 */
public final class NettyMessage {
        private Header header;
        private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}