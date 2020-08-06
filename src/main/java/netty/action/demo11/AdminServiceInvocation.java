package netty.action.demo11;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 12:36
 */
public class AdminServiceInvocation implements InvocationHandler {
    private Object target;

    public AdminServiceInvocation(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("判断用户是否有权限进行操作");
        Object obj = method.invoke(target);
        System.out.println("记录用户执行操作的用户信息、更改内容和时间等");
        return obj;
    }
}