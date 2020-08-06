package netty.action.demo11;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 12:33
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        AdminService adminService = new AdminServiceImpl();
        AdminServiceProxy proxy = new AdminServiceProxy(adminService);
        proxy.update();
        System.out.println("=============================");
        proxy.find();
    }
}