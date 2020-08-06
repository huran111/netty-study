package netty.action.demo10;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 11:30
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String message) {
        System.out.println("服务提供方 HelloServiceImpl#Hello() 被调用");
        // 根据 msg 返回结果
        if (message != null) {
            return "你好，客户端，我已经收到你的消息。你发送的消息是: " + message;
        } else {
            return "你好，客户端，我已经收到你的消息。";
        }
    }
}