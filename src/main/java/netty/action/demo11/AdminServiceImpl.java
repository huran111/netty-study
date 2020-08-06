package netty.action.demo11;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-06 12:32
 */
public class AdminServiceImpl implements AdminService {
    @Override
    public void update() {
        System.out.println("修改管理系统数据");

    }

    @Override
    public Object find() {
        System.out.println("查看管理系统数据");
        return new Object();
    }
}