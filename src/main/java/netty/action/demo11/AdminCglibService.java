package netty.action.demo11;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 12:43
 */
public class AdminCglibService {
    public void update() {
        System.out.println("修改管理系统数据");
    }

    public Object find() {
        System.out.println("查看管理系统数据");
        return new Object();
    }
}