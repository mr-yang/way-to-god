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

    private DistributedLocker locker;
    private CountDownLatch countDownLatch;
    private LockEnum lockEnum;

    public LockRunnable() {
    }

    public LockRunnable(DistributedLocker redisDistributedLocker, CountDownLatch countDownLatch) {
        this.locker = redisDistributedLocker;
        this.countDownLatch = countDownLatch;
    }

    public LockRunnable(DistributedLocker redisDistributedLocker, CountDownLatch countDownLatch, LockEnum lockEnum) {
        this.locker = redisDistributedLocker;
        this.countDownLatch = countDownLatch;
        this.lockEnum = lockEnum;
    }

    @Override
    public void run() {
        switch (lockEnum){
            case NO_LOCK:
                testCount();
                break;
            case REDIS_BIO_LOCK:
                testLock();
                break;
            case REDIS_NIO_LOCK:
                testFastFailLock();
                break;
        }
    }

    private void testFastFailLock() {
        String name = Thread.currentThread().getName();
        log.info(name + " 线程进入 run 方法");
        if (this.locker.tryLock(lockKey)) {
            log.info(name + " 线程获取到了锁");
            try {
                addCont();//执行临界区代码
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.locker.unlock(lockKey);
                log.info(name + "线程释放了锁");
            }
        }
    }

    private void testCount() {
        try {
            countDownLatch.await();
            addCont();//执行临界区代码
        } catch (Exception e) {
            log.error("testCount异常", e);
        }
    }

    private final String lockKey = "redis-lock-test";

    /**
     * 加锁测试
     */
    private void testLock() {
        RLock lock = null;
        try {
            String name = Thread.currentThread().getName();
            log.info(name + " 线程进入 run 方法");
            countDownLatch.await();
            lock = this.locker.lock(lockKey);
            log.info(name + " 线程获取到了锁");
            addCont();//执行临界区代码
        } catch (Exception e) {
            log.error("获取锁异常", e);
        } finally {
            locker.unlock(lock);
            String name = Thread.currentThread().getName();
            log.info(name + "线程释放了锁");
        }
    }

    private void addCont() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        log.info("count的值为 {}", count);
    }
}
