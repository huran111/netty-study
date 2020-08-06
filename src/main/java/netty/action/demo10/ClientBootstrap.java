package netty.action.demo10;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 11:34
 */
public class ClientBootstrap {
    // 定义协议头
    public static final String  providerName = "HelloService#Hello#";

    public static void main(String[] args) {
        // 创建一个消费者
        NettyClient customer = new NettyClient();
        System.out.println("1");
        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        System.out.println("2");
        // 通过代理对象调用服务器提供者的方法（服务）

        String result = service.hello("你好 dubbo");
        System.out.println("3");
        System.out.println("消费者调用提供者方法，返回的结果：result=" + result);
    }
}