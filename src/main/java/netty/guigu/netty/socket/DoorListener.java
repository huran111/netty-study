package netty.guigu.netty.socket;

import java.util.EventListener;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-09 11:07
 */
public interface DoorListener {
    void onMessage(String message);
}