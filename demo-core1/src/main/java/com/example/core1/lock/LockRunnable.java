package com.example.core1.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.CountDownLatch;

/**
 * @author tianxiaoyang
 * @date 2021-08-11 14:42
 * @describe
 */
@Slf4j
public class LockRunnable implements Runnable {


    public static int count = 0;

    private final DistributedLocker locker;
    private final CountDownLatch countDownLatch;

    public LockRunnable(DistributedLocker redisDistributedLocker, CountDownLatch countDownLatch) {
        this.locker = redisDistributedLocker;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        testLockCount();
//        addCont();
    }

    private final String lockKey = "redis-lock-test";

    /**
     * 加锁测试
     */
    private void testLockCount() {
        RLock lock = null;
        try {
            String name = Thread.currentThread().getName();
            log.info(name + " 线程进入 run 方法");
            countDownLatch.await();
            lock = this.locker.lock(lockKey);
            log.info(name + " 线程获取到了锁");
            //执行临界区代码
            addCont();
        } catch (Exception e) {
            log.error("获取锁异常",e);
        } finally {
            locker.unlock(lock);
            String name = Thread.currentThread().getName();
            log.info(name + "线程释放了锁");
        }
    }

    private void addCont() {
        count++;
        log.info("count的值为 {}",count);
    }
}
