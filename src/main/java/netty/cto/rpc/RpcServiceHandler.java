package netty.cto.rpc;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 21:29
 */
public class RpcServiceHandler implements InvocationHandler {
    private final ClientMsgHandler handler;

    public RpcServiceHandler(ClientMsgHandler handler) {
        this.handler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + Arrays.asList(args));
        MyRequest myRequest = new MyRequest();
        myRequest.setReqId(UUID.randomUUID().toString());
        myRequest.setClassName(method.getDeclaringClass().getName());
        myRequest.setMethodName(method.getName());
        myRequest.setArgsCls(method.getParameterTypes());
        myRequest.setArgs(args);
        //调用netty发送数据
        final SyncResponse syncResponse = this.handler.sendRpcRequest(myRequest);
        final Object result = syncResponse.getResult();
        return result;
    }
}