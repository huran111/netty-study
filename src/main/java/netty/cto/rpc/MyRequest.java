package netty.cto.rpc;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 21:53
 */
public class MyRequest implements Serializable {
    private String reqId;
    private String className;
    //方法名称
    private String methodName;
    //方法的类型信息
    private Class[] argsCls;
    private Object[] args;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setArgsCls(Class[] argsCls) {
        this.argsCls = argsCls;
    }

    public Class[] getArgsCls() {
        return argsCls;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "MyRequest{" +
                "reqId='" + reqId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodCls=" + Arrays.toString(argsCls) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}