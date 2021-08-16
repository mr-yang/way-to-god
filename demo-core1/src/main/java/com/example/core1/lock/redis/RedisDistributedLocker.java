package com.example.core1.lock.redis;

import com.example.core1.lock.DistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaoyang
 * @Date: 2020/8/6 15:02
 * @Description: 基于Redisson实现的redis方式的分布式锁
 */
@Slf4j
@Component("redisDistributedLocker")
public class RedisDistributedLocker implements DistributedLocker<RLock> {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public RLock tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public RLock tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {

        try {
            RLock lock = redissonClient.getLock(lockKey);
            if (lock.tryLock(waitTime, leaseTime, unit)) {
                return lock;
            }
        } catch (InterruptedException e) {
            log.error(String.format("尝试获取锁%s异常"), e);

        }
        return null;
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        if (lock != null) {
            lock.unlock();
        }
    }

}
