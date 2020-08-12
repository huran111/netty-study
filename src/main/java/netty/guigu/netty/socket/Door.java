package netty.guigu.netty.socket;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 11:08
 */
public class Door {
    private DoorListener listener;

    public void addListener(DoorListener doorListener) {
        this.listener = doorListener;
    }
    public void send() {
        for (int i = 0; i < 10; i++) {
            listener.onMessage("open the door");
        }
    }
}