package netty.cto.rpc;

/**
 * @program: netty-study
 * @description: 实现类
 * @author: HuRan
 * @create: 2020-08-18 21:34
 */
public class RpcServiceImpl implements RpcService {
    @Override
    public RpcUser rpcLogin(String userName, String pwd) {
        RpcUser rpcUser = new RpcUser();
        rpcUser.setPwd(pwd);
        rpcUser.setUserName(userName);
        return rpcUser;
    }

    @Override
    public RpcUser findUserById(String id) {
        RpcUser rpcUser = new RpcUser();
        rpcUser.setPwd("findUserById");
        rpcUser.setUserName("findUserById");
        return rpcUser;
    }
}