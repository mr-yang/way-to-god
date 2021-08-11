package com.example.core1.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaoyang
 * @Date: 2020/8/6 15:01
 * @Description:
 */
public interface DistributedLocker {
    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);
    boolean tryLock(String lockKey);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}

