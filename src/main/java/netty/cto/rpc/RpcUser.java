package netty.cto.rpc;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 21:29
 */
public class RpcUser {
    private String userName;
    private String pwd;

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "RpcUser{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}