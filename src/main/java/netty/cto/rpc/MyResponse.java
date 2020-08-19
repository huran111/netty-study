package netty.cto.rpc;

import java.io.Serializable;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 22:19
 */
public class MyResponse implements Serializable {
    private String reqId;
    private Object result;

    public void setResult(Object result) {
        this.result = result;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqId() {
        return reqId;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "reqId='" + reqId + '\'' +
                ", result=" + result +
                '}';
    }
}