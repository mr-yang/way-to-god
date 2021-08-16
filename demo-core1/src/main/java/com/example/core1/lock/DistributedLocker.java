package com.example.core1.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaoyang
 * @Date: 2020/8/6 15:01
 * @Description:
 */
public interface DistributedLocker<T> {

    /**
     * 加锁
     *
     * @param lockKey
     * @return
     */
    T lock(String lockKey);

    /**
     * 加锁并带锁的续约时间
     *
     * @param lockKey
     * @return
     */
    T lock(String lockKey, int timeout);

    /**
     * 加锁并带超时时间，如果规定时间内没有获取到锁直接失败
     *
     * @param lockKey
     * @param unit
     * @param timeout
     * @return
     */
    T lock(String lockKey, TimeUnit unit, int timeout);


    T tryLock(String lockKey);

    /**
     * 加锁并带续约时间、超时时间，如果规定时间内没有获取到锁直接失败
     *
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    T tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * 解锁
     *
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 解锁
     *
     * @param lock
     */
    void unlock(T lock);
}

