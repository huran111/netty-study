package netty.cto.rpc;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-18 22:30
 */
public class SyncResponse {
    private String reqId;
    private Object result;
    private AtomicBoolean canGetResult = new AtomicBoolean(false);
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public SyncResponse(String reqId) {
        this.reqId = reqId;
    }

    public Object getResult() {
        lock.lock();
        try {
            while (!canGetResult.get()){
                this.condition.await();
            }
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void setResult(Object result) {
        lock.lock();
        try {
            this.result = result;
            this.canGetResult.set(true);
            this.condition.signalAll();
        } finally {
            lock.unlock();
        }
    }


}