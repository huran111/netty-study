package netty.guigu.netty.socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 11:08
 */
public class Receive {
    public static void test() {
        Door door = new Door();
        door.addListener(new DoorListener() {
            @Override
            public void onMessage(String message) {
                System.out.println("====== result: " + message);
            }
        });

        door.send();
    }

    public static void main(String[] args) {
        test();
    }
}