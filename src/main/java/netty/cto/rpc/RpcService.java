package netty.cto.rpc;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 21:28
 */
public interface RpcService {
    RpcUser rpcLogin(String userName,String pwd);
    RpcUser findUserById(String id);
}